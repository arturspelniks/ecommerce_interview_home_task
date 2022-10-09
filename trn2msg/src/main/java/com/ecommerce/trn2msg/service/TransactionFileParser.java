package com.ecommerce.trn2msg.service;

import com.ecommerce.trn2msg.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

@Service
public class TransactionFileParser {
    @Autowired
    private LogService logService;
    private String transactionsFileName;

    @Value("${transaction.line.length}")
    private  Integer transactionLineLength;

    @Value("${transaction.line.format.regex}")
    private  String transactionLineFormatRegex;

    public ArrayList<Transaction> getTransactionsArrayFromFile() {
        logService.log("Start of transaction file parsing");
        BufferedReader reader;
        ArrayList<Transaction> transactionsArray = new ArrayList<>();
        try {
            reader = new BufferedReader(new FileReader(this.transactionsFileName));
            String line = reader.readLine();
            int lineNumber = 1;
            while (line != null) {
                logService.log(String.format("Parsing line %s", lineNumber));
                if (line.length() == transactionLineLength) {
                    if (line.matches(transactionLineFormatRegex)) {
                        Transaction transaction = new Transaction();
                        try {
                            transaction.setTransactionType(new TransactionType(line));
                        } catch (Exception e) {
                            logService.error(String.format("ERROR: Unable to parse transaction type due to error on line %s:", lineNumber), e);
                            line = reader.readLine();
                            lineNumber++;
                            continue;
                        }

                        try {
                            transaction.setPrimaryAccountNumber(new PrimaryAccountNumber(line));
                        } catch (Exception e) {
                            logService.error(String.format("ERROR: Unable to parse primary account number due to error on line %s:", lineNumber), e);
                            line = reader.readLine();
                            lineNumber++;
                            continue;
                        }

                        try {
                            transaction.setAmountInSubmits(new AmountInSubmits(line));
                        } catch (Exception e) {
                            logService.error(String.format("ERROR: Unable to parse amount in submits due to error on line %s:", lineNumber), e);
                            line = reader.readLine();
                            lineNumber++;
                            continue;
                        }

                        try {
                            transaction.setTransactionTime(new TransactionTime(line));
                        } catch (Exception e) {
                            logService.error(String.format("ERROR: Unable to parse transaction time due to error on line %s:", lineNumber), e);
                            line = reader.readLine();
                            lineNumber++;
                            continue;
                        }

                        try {
                            transaction.setCurrencyCode(new CurrencyCode(line));
                        } catch (Exception e) {
                            logService.error(String.format("ERROR: Unable to parse currency code due to error on line %s:", lineNumber), e);
                            line = reader.readLine();
                            lineNumber++;
                            continue;
                        }

                        transactionsArray.add(transaction);
                    } else {
                        logService.log(String.format("ERROR: Transaction line %s has invalid format", lineNumber));
                    }
                } else {
                    logService.log(String.format("ERROR: Transaction line %s has invalid length", lineNumber));
                }
                line = reader.readLine();
                lineNumber++;
            }
            reader.close();
        } catch (IOException e) {
            logService.error(String.format("ERROR: Unable to read file %s due to error.", this.transactionsFileName), e);
            e.printStackTrace();
        }
        logService.log("End of transaction file parsing");
        return transactionsArray;
    }

    public void setTransactionsFileName(String transactionsFileName) {
        this.transactionsFileName = transactionsFileName;
    }
}
