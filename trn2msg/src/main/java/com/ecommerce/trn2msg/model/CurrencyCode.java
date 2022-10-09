package com.ecommerce.trn2msg.model;

import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Optional;

public class CurrencyCode extends TransactionDataField<String>{
    private final int POSITION_FROM = 44;
    private final int POSITION_TO = 47;
    private String value;

    private static final HashMap<String, String> CURRENCY_CODES = new HashMap<String, String>() {{
        put("840", "usd");
        put("978", "eur");
        put("826", "gbp");
        put("643", "rub");
    }};

    public CurrencyCode(String transactionLine) {
        super(transactionLine);
    }
    @Override
    public void setFieldValue(String transactionLine) {
        String currencyCodeFromLine = transactionLine.substring(POSITION_FROM, POSITION_TO);
        this.value = Optional.ofNullable(CURRENCY_CODES.get(currencyCodeFromLine))
                .orElseThrow(() -> new NoSuchElementException(String.format("Unable to find currency name by currency code %s", currencyCodeFromLine)));;
    }

    public String getValue() {
        return value;
    }
}
