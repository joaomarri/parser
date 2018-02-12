package com.ef;


import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.service.AccessLogService;
import com.ef.service.ParserLogService;

@SpringBootApplication
public class Parser implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(Parser.class);
	
	@Autowired
	private ParserLogService parserLogService;	
	
	@Autowired
	private AccessLogService accessLogService;
	
	@Value("${accesslog}")
	private String accesslog;
	
	@Value("${startDate}")
	private String startDate;

	@Value("${duration}")
	private String duration;

	@Value("${threshold}")
	private Integer threshold;
	
	
	public static void main(String[] args) {
		
		if (args.length != 4) {
			logger.info("Invalid options to run parser !!");
			System.exit(-1);
		}
		
		SpringApplication.run(Parser.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		logger.info("Parser started with arguments: {}", Arrays.toString(args.getSourceArgs()));
        logger.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()){
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        parserLogService.parseLogFileToDatabase(accesslog);
        accessLogService.buildParseResult(accesslog, duration, startDate, threshold);
		
	}
}
