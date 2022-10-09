package com.ecommerce.trn2msg.service;

import com.ecommerce.trn2msg.model.MessageOutputTarget;
import com.ecommerce.trn2msg.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ConversionService {
    @Autowired
    private LogService logService;
    @Autowired
    private TransactionFileParser transactionFileParser;
    @Autowired
    private MessageGenerator messageGenerator;

    public void runConversion(String transactionsFileName, String messagesFileName) {
        logService.log("Start of transaction file conversion");
        MessageOutputTarget messageOutputTarget = MessageOutputTarget.FILE;
        if (messagesFileName.isEmpty()) {
            messageOutputTarget = MessageOutputTarget.CONSOLE;
            logService.log("Message output file name is empty, hence output will be written to the console.");
        }

        transactionFileParser.setTransactionsFileName(transactionsFileName);
        ArrayList<Transaction> transactionsArray = transactionFileParser.getTransactionsArrayFromFile();

        if (messageOutputTarget == MessageOutputTarget.FILE) {
            messageGenerator.setMessageTargetFileName(messagesFileName);
            messageGenerator.generateMessageFileFromTransactionsArray(transactionsArray);
        }
        else {
            messageGenerator.generateAndPrintMessageToConsoleFromTransactionArray(transactionsArray);
        }
        logService.log("End of transaction file conversion");
    }
}
