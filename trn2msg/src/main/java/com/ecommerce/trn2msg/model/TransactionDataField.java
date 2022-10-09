package com.ecommerce.trn2msg.model;

public abstract class TransactionDataField<T> {
    private T value;
    public abstract void setFieldValue(String transactionLine);
    public TransactionDataField(String transactionLine) {
        setFieldValue(transactionLine);
    }
}
