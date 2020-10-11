package com.github.wrapper.bitcoin.utils;

import com.github.wrapper.bitcoin.model.TInput;
import com.github.wrapper.bitcoin.model.TOutput;
import org.bitcoinj.core.*;
import org.bitcoinj.script.Script;
import org.bitcoinj.script.ScriptPattern;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.Objects;

public final class WrapUtils {

    private static final Logger log = LoggerFactory.getLogger(WrapUtils.class);

    public static final int RATE = 8;

    public static final String DETERMINISTIC_PATH_MAIN = "M/44H/0H/0H";

    public static final String DETERMINISTIC_PATH_TEST = "M/44H/1H/0H";

    public static BigDecimal amountRight(BigDecimal amount) {
        return amount.movePointRight(RATE);
    }

    public static BigDecimal amountLeft(BigDecimal amount) {
        return amount.movePointLeft(RATE);
    }

    public static TInput fetchInput(TransactionInput input, NetworkParameters params) {
        try {
            if (input != null) {
                Script script = input.getScriptSig();
                byte[] result = ScriptPattern.extractHashFromP2SH(script);
                ECKey ecKey = ECKey.fromPublicOnly(result);
                Address address = LegacyAddress.fromKey(params, ecKey);
                return new TInput(
                        address.toString(),
                        input.getOutpoint().getIndex(),
                        input.getOutpoint().getHash().toString()
                );
            }
        } catch (Exception ignore) {
        }
        Sha256Hash hash = input.getOutpoint().getHash();
        long index = input.getOutpoint().getIndex();
        return new TInput("", index, hash.toString());
    }

    public static TOutput fetchOutput(TransactionOutput o, NetworkParameters params) {
        try {
            Script script = new Script(o.getScriptBytes());
            String address = script.getToAddress(params, Boolean.TRUE).toString();
            return new TOutput(
                    address,
                    o.getIndex(),
                    Utils.HEX.encode(script.getPubKeyHash()),
                    Utils.HEX.encode(o.getScriptBytes()),
                    o.getValue().value
            );
        } catch (Exception ignore) {
        }
        return null;
    }

}
