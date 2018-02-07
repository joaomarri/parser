package com.ef;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Parser implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(Parser.class);

	
	public static void main(String[] args) {
		
		//if (args.length != 3) {
			//System.out.println("Invalid options to run !!");
			//System.exit(-1);
		//}
		
		SpringApplication.run(Parser.class, args);
	}


	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Application started with arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()){
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        boolean containsOption = args.containsOption("startDate");
        logger.info("Contains startDate: " + containsOption);

		
	}
}
