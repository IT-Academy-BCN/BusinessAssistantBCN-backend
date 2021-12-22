package com.businessassistantbcn.login.config;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoggerConfig {
	
	public static final Logger log = LoggerFactory.getLogger(LoggerConfig.class);
	
	public static void logStackTrace(Exception e) {
		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		e.printStackTrace(pw);
		log.error(sw.toString());
	}
	
}
