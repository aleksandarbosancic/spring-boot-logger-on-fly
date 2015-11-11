/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abosancic.logger.controller;

import com.abosancic.logger.service.LoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author abosancic
 */
@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);
	
	@Autowired
	private LoggerService loggerService;
	
	@RequestMapping( value = "/", method = RequestMethod.GET)
    public String logger(Model model) {
		log.info("Prepare home page....");
		model.addAttribute("loggers", loggerService.getAllLoggers());
        return "home";
    }
	
}
