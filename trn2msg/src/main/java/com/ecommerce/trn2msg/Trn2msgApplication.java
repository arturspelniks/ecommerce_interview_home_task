package com.ecommerce.trn2msg;

import com.ecommerce.trn2msg.service.ConversionService;
import com.ecommerce.trn2msg.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Trn2msgApplication implements CommandLineRunner {
	@Autowired
	private ConversionService conversionService;
	@Autowired
	LogService logService;
	@Override
	public void run(String... args) throws Exception {
		String transactionsFileName = "";
		String messagesFileName = "";
		logService.log("Start of program execution");

		try {
			transactionsFileName = args[0];
			logService.log(String.format("Transaction file received: %s", transactionsFileName));

			try {
				messagesFileName = args[1];
				logService.log(String.format("Message output file name: %s", messagesFileName));
			} catch (ArrayIndexOutOfBoundsException e) {
				logService.log("Message output file name is empty, hence output will be written to the console.");
			}

			conversionService.runConversion(transactionsFileName, messagesFileName);
		} catch (ArrayIndexOutOfBoundsException e) {
			logService.log("ERROR: Transaction file name cannot be empty.");
		} catch (Exception e) {
			logService.error("ERROR: Unable to convert file due to error.", e);
		}

		logService.log("End of program execution");

	}

	public static void main(String[] args) {
		SpringApplication.run(Trn2msgApplication.class, args);
	}
}
