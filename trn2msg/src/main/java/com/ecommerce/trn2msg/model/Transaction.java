package com.ecommerce.trn2msg.model;

public class Transaction {

    private TransactionType transactionType;
    private PrimaryAccountNumber primaryAccountNumber;
    private AmountInSubmits amountInSubmits;
    private TransactionTime transactionTime;
    private CurrencyCode currencyCode;

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public PrimaryAccountNumber getPrimaryAccountNumber() {
        return primaryAccountNumber;
    }

    public void setPrimaryAccountNumber(PrimaryAccountNumber primaryAccountNumber) {
        this.primaryAccountNumber = primaryAccountNumber;
    }

    public AmountInSubmits getAmountInSubmits() {
        return amountInSubmits;
    }

    public void setAmountInSubmits(AmountInSubmits amountInSubmits) {
        this.amountInSubmits = amountInSubmits;
    }

    public TransactionTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(TransactionTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public CurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(CurrencyCode currencyCode) {
        this.currencyCode = currencyCode;
    }
}
