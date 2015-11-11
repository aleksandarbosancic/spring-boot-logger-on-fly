/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abosancic.logger.service;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.abosancic.logger.models.LoggerDTO;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *  level of 
 *	request p	      effective level q
 *  ********************************************* 
 *			TRACE	DEBUG	INFO	WARN	ERROR	OFF
 *	TRACE	YES		NO		NO		NO		NO		NO
 *	DEBUG	YES		YES		NO		NO		NO		NO
 *	INFO	YES		YES		YES		NO		NO		NO
 *	WARN	YES		YES		YES		YES		NO		NO 
 *	ERROR	YES		YES		YES		YES		YES		NO
 *
 * @author abosancic
 */
@Component
public class LoggerService {
	
	private static final Logger log = LoggerFactory.getLogger(LoggerService.class);

	private Map<String, Level> originalLevels = new TreeMap<>();
	
	@PostConstruct
	private void init(){
		log.info("Loggers and levels:");
		originalLevels = getAllLevels();
		originalLevels.forEach((key, val) -> {
			log.info("logger: {}, level: {}", key, val);
		});
	}
	
	public void print() {
		log.error("Message logged at ERROR level");
	    log.warn("Message logged at WARN level");
		log.info("Message logged at INFO level");
	    log.debug("Message logged at DEBUG level");
	    log.trace("Message logged at TRACE level");
	}
	
	public void changeToInit() {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.getLoggerList().stream()
			.filter((logger) -> (logger.getLevel() != null))
			.forEach((logger) -> {
				Level level = originalLevels.get(logger.getName());
				logger.setLevel(level);
			});
	}
	
	public void changeLevel(final Level level) {
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.getLoggerList().stream()
			.filter((logger) -> (logger.getLevel() != null))
			.forEach((logger) -> {
				logger.setLevel(level);
			});
	}
	
	private Map<String, Level> getAllLevels() {
		Map<String, Level> levels = new TreeMap<>();
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.getLoggerList().stream()
			.filter((logger) -> (logger.getLevel() != null))
			.forEach((logger) -> {
				levels.put(logger.getName(), logger.getLevel());
			});
		return levels;
	}
	
	public List<LoggerDTO> getAllLoggers() {
		List loggers = new ArrayList();
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		context.getLoggerList().stream()
			.filter((logger) -> (logger.getLevel() != null))
			.forEach((logger) -> {
				LoggerDTO ldto = new LoggerDTO();
				ldto.setLevel(logger.getLevel().toString());
				ldto.setLogger(logger.getName());
				loggers.add(ldto);
			});
		return loggers;
	}

	public String getLogFile(Long page, Long size) {
		Path path = Paths.get("C:\\work\\Repository\\github\\spring-boot-logger-on-fly\\logs", "rest-demo.log");
		StringBuilder builder = new StringBuilder();
		//The stream hence file will also be closed here
		try (Stream<String> lines = Files.lines(path, StandardCharsets.UTF_8)) {
			lines
				.skip(((page-1) * size))
				.limit(size)
				.peek(line -> {
					builder.append(line).append("\n");
				})
				.forEachOrdered(System.out::println);
		} catch (IOException ex) {
			java.util.logging.Logger.getLogger(LoggerService.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		return builder.toString();
	}
	
}
