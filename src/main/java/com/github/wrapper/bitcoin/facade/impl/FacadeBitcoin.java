package com.github.wrapper.bitcoin.facade.impl;

import com.github.wrapper.bitcoin.controllers.IBlockController;
import com.github.wrapper.bitcoin.controllers.impl.BlockController;
import com.github.wrapper.bitcoin.facade.IFacadeBitcoin;
import com.github.wrapper.bitcoin.model.*;
import com.github.wrapper.bitcoin.payload.BlockChainInfo;
import com.github.wrapper.bitcoin.utils.Network;
import com.github.wrapper.bitcoin.utils.WrapWallet;
import com.google.common.collect.ImmutableList;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.script.Script;
import org.bitcoinj.utils.BriefLogFormatter;
import org.bitcoinj.wallet.DeterministicKeyChain;
import org.bitcoinj.wallet.DeterministicSeed;
import org.bitcoinj.wallet.SendRequest;
import org.bitcoinj.wallet.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.wrapper.bitcoin.model.TransactionData.transaction;

public final class FacadeBitcoin implements IFacadeBitcoin {

    private static final Logger log = LoggerFactory.getLogger(FacadeBitcoin.class);

    private static final int ZERO = 0;

    private static final int ONE = 1;

    private static final int TEN = 10;

    private static final int THIRTY = 30;

    private static final int ONE_HUNDRED_TWENTY_EIGHT = 128;

    private final NetworkParameters params;

    private final String derivation;

    private final WrapWallet wallet;

    private final int period;

    private AtomicLong count;

    public FacadeBitcoin(Network network, String derivation, String path, String walletName, int period) {
        this.params = network.get();
        this.derivation = derivation;
        this.wallet = new WrapWallet(network.get(),
                new File(path), walletName);
        this.period = period;
    }

    @Override
    public KeysBag generateKeys() {
        SecureRandom sr = new SecureRandom();
        DeterministicSeed seed = new DeterministicSeed(sr, ONE_HUNDRED_TWENTY_EIGHT, "");
        String mnemonic = String.join(" ",
                Objects.requireNonNull(seed.getMnemonicCode()));
        long timeCreating = System.currentTimeMillis();
        DeterministicKeyChain kc = DeterministicKeyChain.builder()
                .seed(seed).outputScriptType(Script.ScriptType.P2PKH)
                .accountPath(DeterministicKeyChain.ACCOUNT_ZERO_PATH).build();
        List<ChildNumber> result = HDUtils.parsePath(this.derivation);
        DeterministicKey keys = kc.getKeyByPath(result, Boolean.TRUE);
        String chainCode = Utils.HEX.encode(keys.getChainCode());
        String privateKey = Utils.HEX.encode(keys.getPrivKey().toByteArray());
        byte[] array = keys.getPubKey();
        String publicKey = Utils.HEX.encode(array);
        return new KeysBag(mnemonic, privateKey, publicKey, chainCode, timeCreating);
    }

    @Override
    public List<ChainAddress> generateAddresses(KeysBag keysBag, int start, int amount) {
        byte[] pubKey = Utils.HEX.decode(keysBag.getPublicKey());
        byte[] chainCode = Utils.HEX.decode(keysBag.getChainCode());
        ImmutableList<ChildNumber> pathList = ImmutableList.<ChildNumber>builder()
                .addAll(HDUtils.parsePath(this.derivation)).build();
        ECKey publicOnly = DeterministicKey.fromPublicOnly(pubKey);
        DeterministicKey keys = new DeterministicKey(pathList, chainCode,
                publicOnly.getPubKeyPoint(), (BigInteger) null, (DeterministicKey) null);
        DeterministicKey parentKey = HDKeyDerivation
                .deriveChildKey(keys, new ChildNumber(ZERO, Boolean.FALSE));
        return IntStream.rangeClosed(start, amount + start)
                .mapToObj(i -> this.createAddress(i, parentKey))
                .collect(Collectors.toList());
    }

    @Override
    public ResponseTrx send(String transaction) {
        byte[] array = Utils.HEX.decode(transaction);
        Transaction t = new Transaction(this.params, array);
        SendRequest request = SendRequest.forTx(t);
        try {
            Wallet.SendResult result = this.wallet.wallet().sendCoins(request);
            Transaction response = result.broadcastComplete.get(THIRTY, TimeUnit.SECONDS);
            String hash = response.getTxId().toString();
            return new ResponseTrx(hash, response.getInputs().stream()
                    .map(SpentInput::instance)
                    .collect(Collectors.toList()),
                    Boolean.FALSE, null
            );
        } catch (InsufficientMoneyException | InterruptedException | ExecutionException | TimeoutException e) {
            log.error("Enter: {}", e.getMessage());
            return new ResponseTrx(t.getTxId().toString(),
                    t.getInputs().stream()
                            .map(SpentInput::instance)
                            .collect(Collectors.toList()),
                    Boolean.TRUE,
                    new ResponseTrx.Error(e.getMessage())
            );
        }
    }

    @Override
    public void networking() {
        BriefLogFormatter.init();
        this.wallet.wrapStartAsync();
        this.wallet.wrapAwaitRun();
        this.wallet.addPeer(ONE);
        this.wallet.peerGroup();
    }

    @Override
    public NewBlock fetchBlock(String hash, long height) {
        PeerGroup pg = this.wallet.peerGroup();
        List<Peer> peers = pg.getConnectedPeers();
        List<TransactionData> transactions = peers.stream()
                .map(peer -> fetchBlock(peer, hash))
                .filter(Objects::nonNull)
                .findFirst().stream()
                .filter(b -> Objects.nonNull(b.getTransactions()))
                .flatMap(b -> b.getTransactions().stream())
                .filter(Objects::nonNull)
                .map(t -> transaction(t, this.params))
                .collect(Collectors.toList());
        return new NewBlock(height, hash, transactions);
    }

    @Override
    public NewBlock fetchBlock(Long height, String url, Consumer<NewBlock> blocks) {
        if (Objects.nonNull(height)) {
            this.count = new AtomicLong(height);
            IBlockController controller = new BlockController(url);
            Executors.newSingleThreadScheduledExecutor()
                    .scheduleAtFixedRate(
                            () -> fetchBlock(controller, blocks),
                            ZERO,
                            this.period,
                            TimeUnit.MINUTES
                    );
        }
        return null;
    }

    @Override
    public BlockChainInfo fetchInfo(String url) {
        return new BlockController(url).findBlockChainInfo();
    }

    @Override
    public NetworkParameters getParams() {
        return this.params;
    }

    @Override
    public String getDerivation() {
        return this.derivation;
    }

    private void fetchBlock(IBlockController controller, Consumer<NewBlock> blocks) {
        try {
            controller.findBlockHash(this.count.incrementAndGet())
                    .ifPresentOrElse(response -> {
                        NewBlock block = fetchBlock(response.getBlockHash(), response.getBlockNumber());
                        CompletableFuture.runAsync(() -> blocks.accept(block));
                    }, () -> this.count.decrementAndGet());
        } catch (Throwable e) {
            log.error("Enter: {}", e.getMessage());
        }
    }

    private ChainAddress createAddress(int index, DeterministicKey parentKey) {
        DeterministicKey keys = HDKeyDerivation
                .deriveChildKey(parentKey, new ChildNumber(index, Boolean.FALSE));
        String address = LegacyAddress.fromPubKeyHash(this.params, keys.getPubKeyHash()).toString();
        return new ChainAddress(index, address);
    }

    private Block fetchBlock(Peer peer, String hash) {
        try {
            return peer.getBlock(Sha256Hash.wrap(hash))
                    .get(TEN, TimeUnit.SECONDS);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            log.warn("Enter: {}", e.getMessage());
        }
        return null;
    }

}
