package com.ecommerce.trn2msg.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionTime extends TransactionDataField<LocalDateTime>{
    private final int POSITION_FROM = 30;
    private final int POSITION_TO = 44;
    private final String DATE_TIME_FORMAT = "yyyyMMddHHmmss";
    private LocalDateTime value;
    public TransactionTime(String transactionLine) {
        super(transactionLine);
    }
    @Override
    public void setFieldValue(String transactionLine) {
        String transactionTimeString = transactionLine.substring(POSITION_FROM, POSITION_TO);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
        this.value = LocalDateTime.parse(transactionTimeString, formatter);
    }

    public LocalDateTime getValue() {
        return value;
    }
}