package com.github.facade.bitcoin.transaction;

import com.github.facade.bitcoin.exceptions.ErrorFee;
import com.github.facade.bitcoin.exceptions.NotEnoughMoney;
import com.github.facade.bitcoin.models.ChainAddress;
import com.github.facade.bitcoin.models.UnspentOutput;
import com.github.facade.bitcoin.utils.Network;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.bitcoinj.core.*;
import org.bitcoinj.crypto.ChildNumber;
import org.bitcoinj.crypto.DeterministicKey;
import org.bitcoinj.crypto.HDKeyDerivation;
import org.bitcoinj.crypto.HDUtils;
import org.bitcoinj.script.Script;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;

public final class NewTransaction {

    private static final BigDecimal DEFAULT_FEE =
            new BigDecimal(Transaction.DEFAULT_TX_FEE.value);

    private final String hash;

    private final BigDecimal totalAmount;

    private final List<UnspentOutput> unspentOutputs;

    private final BigDecimal change;

    private final BigDecimal fee;

    private final String trxHex;

    private NewTransaction(Builder b) {
        this.totalAmount = b.totalAmount;
        this.unspentOutputs = b.unspentOutputs;
        this.change = b.change;
        this.fee = b.fee;
        this.trxHex = b.trxHex;
        this.hash = b.hash;
    }

    public static class Builder {

        // Generate params.

        private NetworkParameters parameters;

        private String privateKey;

        private String chainCode;

        private ChainAddress from;

        private String to;

        private BigDecimal amount;

        private List<UnspentOutput> outputs;

        private BigDecimal feePerKb;

        private String deterministic;

        //Transaction params

        private Transaction transaction;

        private BigDecimal totalAmount;

        private List<UnspentOutput> unspentOutputs;

        private BigDecimal change;

        private BigDecimal fee;

        private String trxHex;

        private String hash;

        //end

        public Builder parameters(Network network) {
            this.parameters = network.get();
            return this;
        }

        public Builder deterministic(String deterministic) {
            this.deterministic = deterministic;
            return this;
        }

        public Builder privateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        public Builder chainCode(String chainCode) {
            this.chainCode = chainCode;
            return this;
        }

        public Builder from(ChainAddress from) {
            this.from = from;
            return this;
        }

        public Builder to(String to) {
            this.to = to;
            return this;
        }

        public Builder amount(BigDecimal amount) {
            this.amount = amount;
            return this;
        }

        public Builder outputs(List<UnspentOutput> outputs) {
            this.outputs = outputs;
            return this;
        }

        public Builder feePerKb(BigDecimal feePerKb) {
            this.feePerKb = feePerKb;
            return this;
        }

        @Deprecated
        public Builder calcUnspentOutput() {
            calculation(DEFAULT_FEE);
            return this;
        }

        @Deprecated
        public Builder transaction() {
            notSignTrx();
            BigDecimal fee = calculateFee();
            calculation(fee);
            signTrx();
            this.hash = this.transaction.getTxId().toString();
            byte[] bytes = this.transaction.unsafeBitcoinSerialize();
            this.fee = fee;
            this.trxHex = hex(bytes);
            return this;
        }

        public Builder transaction(Claim claim) {
            if (claim.isFull()) {
                BigDecimal value = this.amount.subtract(DEFAULT_FEE);
                calculation(DEFAULT_FEE, value);
                notSignTrx();
                BigDecimal fee = calculateFee();
                value = this.amount.subtract(fee);
                calculation(fee, value);
                signTrx();
                this.hash = this.transaction.getTxId().toString();
                byte[] bytes = this.transaction.unsafeBitcoinSerialize();
                this.fee = fee;
                this.trxHex = hex(bytes);
            } else {
                calculation(DEFAULT_FEE);
                notSignTrx();
                BigDecimal fee = calculateFee();
                calculation(fee);
                signTrx();
                this.hash = this.transaction.getTxId().toString();
                byte[] bytes = this.transaction.unsafeBitcoinSerialize();
                this.fee = fee;
                this.trxHex = hex(bytes);
            }
            return this;
        }

        public NewTransaction build() {
            return new NewTransaction(this);
        }

        private void calculation(BigDecimal fee) {
            BigDecimal overFlow = BigDecimal.ZERO;
            this.totalAmount = this.amount.add(fee);
            this.unspentOutputs = Lists.newArrayList();
            for (UnspentOutput o : this.outputs) {
                overFlow = overFlow.add(o.getAmount());
                this.unspentOutputs.add(o);
                if (overFlow.doubleValue() >= this.totalAmount.doubleValue()) {
                    break;
                }
            }
            if (overFlow.doubleValue() < this.totalAmount.doubleValue()) {
                throw new NotEnoughMoney("Balance is not enough.");
            }
            this.change = overFlow.subtract(this.totalAmount);
        }

        private void calculation(BigDecimal fee, BigDecimal amount) {
            BigDecimal overFlow = BigDecimal.ZERO;
            this.totalAmount = amount.add(fee);
            this.unspentOutputs = Lists.newArrayList();
            for (UnspentOutput o : this.outputs) {
                overFlow = overFlow.add(o.getAmount());
                this.unspentOutputs.add(o);
                if (overFlow.doubleValue() >= this.totalAmount.doubleValue()) {
                    break;
                }
            }
            if (overFlow.doubleValue() < this.totalAmount.doubleValue()) {
                throw new NotEnoughMoney("Balance is not enough.");
            }
            this.change = overFlow.subtract(this.totalAmount);
        }

        private void notSignTrx() {
            this.transaction = new Transaction(this.parameters);
            Address addressTo = Address.fromString(this.parameters, this.to);
            this.transaction.addOutput(Coin.parseCoin(
                    this.amount.toPlainString()), addressTo
            );
            if (change.doubleValue() != 0.0) {
                Address addressChange = Address.fromString(this.parameters, this.from.getAddress());
                this.transaction.addOutput(
                        Coin.parseCoin(this.change.toPlainString()),
                        addressChange
                );
            }
            this.unspentOutputs.forEach(o -> {
                Sha256Hash sha256Hash = Sha256Hash.wrap(Utils.HEX.decode(o.getTxHash()));
                Script script = new Script(Utils.HEX.decode(o.getTxOutScriptPubKey()));
                this.transaction.addInput(sha256Hash, o.getIndex(), script);
            });
            this.transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        }

        private void signTrx() {
            this.transaction = new Transaction(this.parameters);
            Address addressTo = Address.fromString(this.parameters, this.to);
            this.transaction.addOutput(Coin.parseCoin(this.amount.toPlainString()), addressTo);
            if (Objects.nonNull(change) && change.doubleValue() != 0.0) {
                Address addressChange = Address.fromString(this.parameters, this.from.getAddress());
                this.transaction.addOutput(
                        Coin.parseCoin(this.change.toPlainString()),
                        addressChange
                );
            }
            DeterministicKey key = restoreKey(this.privateKey, this.chainCode, this.from.getIndex());
            this.unspentOutputs.forEach(o -> {
                TransactionOutPoint outPoint = input(o);
                Script script = new Script(Utils.HEX.decode(o.getTxOutScriptPubKey()));
                this.transaction.addSignedInput(outPoint, script, key,
                        Transaction.SigHash.ALL, Boolean.TRUE);
            });
            this.transaction.setPurpose(Transaction.Purpose.USER_PAYMENT);
        }

        private DeterministicKey restoreKey(String privateKey, String chainCode, int index) {
            byte[] arrayPrivateKey = Utils.HEX.decode(privateKey);
            BigInteger parentBigKey = new BigInteger(arrayPrivateKey);
            ECKey parentECKey = ECKey.fromPrivate(arrayPrivateKey);
            byte[] arrayChainCode = Utils.HEX.decode(chainCode);
            ImmutableList<ChildNumber> pathList = ImmutableList.<ChildNumber>builder()
                    .addAll(HDUtils.parsePath(this.deterministic)).build();
            DeterministicKey parentPrivateKey = new DeterministicKey(pathList, arrayChainCode,
                    parentECKey.getPubKeyPoint(), parentBigKey, (DeterministicKey) null);
            DeterministicKey childKey = HDKeyDerivation
                    .deriveChildKey(parentPrivateKey, new ChildNumber(index, Boolean.FALSE));
            return HDKeyDerivation.deriveChildKey(childKey, new ChildNumber(index, Boolean.FALSE));
        }

        private BigDecimal calculateFee() {
            byte[] bytes = this.transaction.unsafeBitcoinSerialize();
            int txSizeKb = (int) Math.ceil(bytes.length / 1024.);
            BigDecimal fee = this.feePerKb.multiply(new BigDecimal(txSizeKb));
            if (fee.doubleValue() > DEFAULT_FEE.doubleValue()) {
                throw new ErrorFee("Not normal commission.");
            }
            return fee;
        }

        private String hex(byte[] bytes) {
            return Utils.HEX.encode(bytes);
        }

        private TransactionOutPoint input(UnspentOutput o) {
            Sha256Hash sha256Hash = Sha256Hash.wrap(Utils.HEX.decode(o.getTxHash()));
            return new TransactionOutPoint(this.parameters, o.getIndex(), sha256Hash);
        }

    }

    public final BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public final List<UnspentOutput> getUnspentOutputs() {
        return unspentOutputs;
    }

    public final BigDecimal getChange() {
        return change;
    }

    public final BigDecimal getFee() {
        return fee;
    }

    public final String getTrxHex() {
        return trxHex;
    }

    public final String getHash() {
        return hash;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewTransaction that = (NewTransaction) o;
        return Objects.equals(hash, that.hash) &&
                Objects.equals(totalAmount, that.totalAmount) &&
                Objects.equals(unspentOutputs, that.unspentOutputs) &&
                Objects.equals(change, that.change) &&
                Objects.equals(fee, that.fee) &&
                Objects.equals(trxHex, that.trxHex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hash, totalAmount, unspentOutputs, change, fee, trxHex);
    }

    @Override
    public String toString() {
        return "NewTransaction{" +
                "hash='" + hash + '\'' +
                ", totalAmount=" + totalAmount +
                ", unspentOutputs=" + unspentOutputs +
                ", change=" + change +
                ", fee=" + fee +
                ", trxHex='" + trxHex + '\'' +
                '}';
    }
}
