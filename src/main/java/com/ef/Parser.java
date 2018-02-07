package com.ef;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ef.model.AccessLog;
import com.ef.service.ReaderLogService;
import com.ef.service.WriterLogService;

@SpringBootApplication
public class Parser implements ApplicationRunner {

	private static final Logger logger = LoggerFactory.getLogger(Parser.class);

	@Autowired
	private ReaderLogService readerLogService;
	
	@Autowired
	private WriterLogService writerLogService;
	
	
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
        //logger.info("NonOptionArgs: {}", args.getNonOptionArgs());
        logger.info("OptionNames: {}", args.getOptionNames());

        for (String name : args.getOptionNames()){
            logger.info("arg-" + name + "=" + args.getOptionValues(name));
        }

        boolean containsOption = args.containsOption("startDate");
        logger.info("Contains startDate: " + containsOption);

        readerLogService.readLogFile("access.log");
        //mock
        List<AccessLog> logs = new ArrayList<>();
        AccessLog accessLog = new AccessLog();
        accessLog.setDate(new Date());
        logs.add(accessLog);
		writerLogService.persistLogs(logs);
	}
}
