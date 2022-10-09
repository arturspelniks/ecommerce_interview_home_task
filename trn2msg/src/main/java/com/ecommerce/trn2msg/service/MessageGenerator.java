package com.ecommerce.trn2msg.service;

import com.ecommerce.trn2msg.model.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
@Service
public class MessageGenerator {
    @Autowired
    private LogService logService;
    private String messageTargetFileName;
    private final String MESSAGE_DATE_TIME_FORMAT = "dd.MM.yyyy HH:mm";
    private final String TOTALS_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private JSONObject messageJSONObject = new JSONObject();

    private BigDecimal getAmountInSubmitsSum(ArrayList<Transaction> transactionsArray) {
        BigDecimal amountInSubmitsSum = transactionsArray.stream()
                .map(transaction -> transaction.getAmountInSubmits().getValue())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return amountInSubmitsSum;
    }

    private String getTransactionMessage(Transaction transaction) {
        String transactionMessage = String.format("%s with card %s on %s, amount %s %s.",
                transaction.getTransactionType().getValue(),
                transaction.getPrimaryAccountNumber().getMaskedPrimaryAccountNumber(),
                getDateInFormat(transaction.getTransactionTime().getValue(), MESSAGE_DATE_TIME_FORMAT),
                transaction.getAmountInSubmits().getValue(),
                transaction.getCurrencyCode().getValue());

        return transactionMessage;
    }

    private String getDateInFormat(LocalDateTime date, String format) {
        DateTimeFormatter formatter =  DateTimeFormatter.ofPattern(format);
        return date.format(formatter);
    }

    private void setMessageJSONObject(ArrayList<Transaction> transactionsArray) {
        logService.log("Start of JSON object generation");
        JSONObject messageRootDetails = new JSONObject();
        JSONArray messageList = new JSONArray();

        transactionsArray.forEach(transaction -> {
            messageList.put(getTransactionMessage(transaction));
        });

        JSONObject messageTotalsDetails = new JSONObject();
        messageTotalsDetails.put("cnt", transactionsArray.size());
        messageTotalsDetails.put("sum", getAmountInSubmitsSum(transactionsArray));
        messageTotalsDetails.put("date", getDateInFormat(LocalDateTime.now(), TOTALS_DATE_TIME_FORMAT));
        messageRootDetails.put("msg-list", messageList);
        messageRootDetails.put("totals", messageTotalsDetails);

        this.messageJSONObject.put("root", messageRootDetails);
        logService.log("End of JSON object generation");
    }

    public void generateMessageFileFromTransactionsArray(ArrayList<Transaction> transactionsArray) {
        logService.log("Start of message file generation");
        setMessageJSONObject(transactionsArray);

        try (FileWriter file = new FileWriter(messageTargetFileName)) {
            file.write(messageJSONObject.toString(4));
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
            logService.error("ERROR: Unable to create JSON file due to error.", e);
        }

        logService.log("End of message file generation");
    }

    public void generateAndPrintMessageToConsoleFromTransactionArray(ArrayList<Transaction> transactionsArray) {
        logService.log("Start of message printing to console");
        setMessageJSONObject(transactionsArray);

        System.out.println(messageJSONObject.toString(4));
        logService.log("End of message printing to console");
    }

    public void setMessageTargetFileName(String messageTargetFileName) {
        this.messageTargetFileName = messageTargetFileName;
    }
}
