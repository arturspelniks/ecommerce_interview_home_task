package com.ecommerce.trn2msg.model;

public class PrimaryAccountNumber extends TransactionDataField<String>{
    private final int POSITION_FROM = 2;
    private final int POSITION_TO = 18;
    private String value;
    private String maskedPrimaryAccountNumber;
    private final String PRIMARY_ACCOUNT_NUMBER_MASKED_PART = "XXXXXX";
    private final int PRIMARY_ACCOUNT_NUMBER_FIRST_PART_LENGTH = 6;
    public PrimaryAccountNumber(String transactionLine) {
        super(transactionLine);
        setMaskedPrimaryAccountNumber();
    }

    public String getMaskedPrimaryAccountNumber() {
        return maskedPrimaryAccountNumber;
    }

    private void setMaskedPrimaryAccountNumber() {
        String firstPrimaryAccountNumberPart = this.value.substring(0, PRIMARY_ACCOUNT_NUMBER_FIRST_PART_LENGTH);
        String lastPrimaryAccountNumberPart = this.value.substring(PRIMARY_ACCOUNT_NUMBER_FIRST_PART_LENGTH + PRIMARY_ACCOUNT_NUMBER_MASKED_PART.length(), this.value.length());
        this.maskedPrimaryAccountNumber = firstPrimaryAccountNumberPart + PRIMARY_ACCOUNT_NUMBER_MASKED_PART + lastPrimaryAccountNumberPart;
    }
    @Override
    public void setFieldValue (String transactionLine) {
        this.value = transactionLine.substring(POSITION_FROM, POSITION_TO);
    }
}
