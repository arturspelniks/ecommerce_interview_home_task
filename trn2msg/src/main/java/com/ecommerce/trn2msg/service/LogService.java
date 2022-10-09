package com.ecommerce.trn2msg.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.*;
import java.io.IOException;

@Service
public class LogService {
    public final Logger logger = Logger.getLogger(LogService.class.getName());
    private FileHandler fileHandler = null;

    private LogService(@Value("${spring.application.name}") String applicationName, @Value("${log.message.format}") String logMessageFormat, @Value("${log.file.date.format}") String logFileDateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(logFileDateFormat);
        System.setProperty("java.util.logging.SimpleFormatter.format", logMessageFormat);
        try {
            fileHandler = new FileHandler(String.format("%s_%s.log", applicationName, format.format(new Date())), true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SimpleFormatter simpleFormatter = new SimpleFormatter();
        fileHandler.setFormatter(simpleFormatter);
        logger.addHandler(fileHandler);
        logger.setLevel(Level.ALL);
    }

    public void log(String message) {
        logger.info(message);
    }

    public void error(String message, Throwable thrown) {
        logger.log(Level.ALL, message, thrown);
    }
}
