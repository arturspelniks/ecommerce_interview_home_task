package com.ecommerce.trn2msg.model;

import java.math.BigDecimal;

public class AmountInSubmits extends TransactionDataField<BigDecimal>{
    private final int POSITION_FROM = 18;
    private final int POSITION_TO = 30;
    private final int INTEGER_PART_LENGTH = 10;
    private final int FRACTIONAL_PART_LENGTH = 2;
    private BigDecimal value;
    private final String DECIMAL_SEPARATOR = ".";

    public AmountInSubmits(String transactionLine) {
        super(transactionLine);
    }
    @Override
    public void setFieldValue(String transactionLine) {
        String amountInSubmitsString = transactionLine.substring(POSITION_FROM, POSITION_TO);
        String amountInSubmitsStringFinal = amountInSubmitsString.substring(0, INTEGER_PART_LENGTH) + DECIMAL_SEPARATOR + amountInSubmitsString.substring(INTEGER_PART_LENGTH, INTEGER_PART_LENGTH + FRACTIONAL_PART_LENGTH);
        this.value = new BigDecimal(amountInSubmitsStringFinal);
    }
    public BigDecimal getValue() {
        return value;
    }

}
