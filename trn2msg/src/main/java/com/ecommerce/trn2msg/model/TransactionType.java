package com.ecommerce.trn2msg.model;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TransactionType extends TransactionDataField<String>{
    private final int POSITION_FROM = 0;
    private final int POSITION_TO = 2;
    private String value;

    private static final HashMap<String, String> TRANSACTION_TYPES = new HashMap<String, String>() {{
        put("00", "purchase");
        put("01", "withdrawal");
    }};

    public TransactionType(String transactionLine) {
        super(transactionLine);
    }

    @Override
    public void setFieldValue(String transactionLine) {
        String transactionCodeFromLine = transactionLine.substring(POSITION_FROM, POSITION_TO);
        this.value = Optional.ofNullable(TRANSACTION_TYPES.get(transactionCodeFromLine))
                .orElseThrow(() -> new NoSuchElementException(String.format("Unable to find transaction type name by transaction type code %s", transactionCodeFromLine)));;
    }

    public String getValue() {
        return value;
    }
}
