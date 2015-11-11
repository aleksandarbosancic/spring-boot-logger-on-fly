/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abosancic.logger.controller;

import com.abosancic.logger.service.LoggerService;
import ch.qos.logback.classic.Level;
import java.util.List;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author abosancic
 */
@RestController
@RequestMapping("/logger")
public class LoggerController {
	
	private static final Logger log = LoggerFactory.getLogger(LoggerController.class);
	
	@Autowired
	private LoggerService loggerService;
	
	@PostConstruct
	private void init(){
		loggerService.print();
	}
	
	@RequestMapping(method = RequestMethod.GET)
    public @ResponseBody List logger() {
		return loggerService.getAllLoggers();
    }
	
	@RequestMapping(method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
    public void setLogger(@RequestParam String level) {
		loggerService.changeLevel(Level.toLevel(level));
    }
	
	@RequestMapping(method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.OK)
    public void resetLogger() {
		loggerService.changeToInit();
    }
	
	@RequestMapping(value = "/test", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
    public void test() {
		loggerService.print();
		log.error("Message logged at ERROR level");
		log.warn("Message logged at WARN level");
		log.info("Message logged at INFO level");
		log.debug("Message logged at DEBUG level");
		log.trace("Message logged at TRACE level");
		loggerService.print();
    }
	
	@RequestMapping( value = "/logfile", method = RequestMethod.GET)
    public String getlogfile(@RequestParam Long page, @RequestParam Long size) {
		log.info("Get file part: page: {}, size: {}", page, size);
        return loggerService.getLogFile(page, size);
    }
	
}
