/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abosancic.logger.controller;

import com.abosancic.logger.service.LoggerService;
import ch.qos.logback.classic.Level;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web .bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abosancic
 */
@RestController
public class LoggerController {
	
	private static final Logger log = LoggerFactory.getLogger(LoggerController.class);
	
	@Autowired
	private LoggerService loggerService;
	
	@PostConstruct
	private void init(){
		loggerService.print();
	}
	
	@RequestMapping("/logger")
    public String logger() {
		log.error("Message logged at ERROR level");
	    log.warn("Message logged at WARN level");
		log.info("Message logged at INFO level");
	    log.debug("Message logged at DEBUG level");
        return "Greetings from Spring Boot!";
    }
	
	@RequestMapping(value = "/logger", method = RequestMethod.PUT)
    public String setLogger(@RequestParam String level) {
		loggerService.changeLevel(Level.toLevel(level));
		log.error("Message logged at ERROR level");
	    log.warn("Message logged at WARN level");
		log.info("Message logged at INFO level");
	    log.debug("Message logged at DEBUG level");
		log.trace("Message logged at TRACE level");
		return "Greetings from Spring Boot!";
    }
	
	@RequestMapping(value = "/logger", method = RequestMethod.DELETE)
    public String resetLogger() {
		loggerService.changeToInit();
		log.error("Message logged at ERROR level");
	    log.warn("Message logged at WARN level");
		log.info("Message logged at INFO level");
	    log.debug("Message logged at DEBUG level");
		log.trace("Message logged at TRACE level");
		return "Greetings from Spring Boot!";
    }
	
	@RequestMapping("/loggerp")
    public String loggerp() {
		loggerService.print();
		return "Greetings from Spring Boot!";
    }
	
}
