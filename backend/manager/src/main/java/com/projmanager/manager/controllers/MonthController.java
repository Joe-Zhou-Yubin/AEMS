package com.projmanager.manager.controllers;

import java.text.ParseException;
import java.util.Date;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanager.manager.models.Month;
import com.projmanager.manager.repository.MonthRepository;
import java.text.SimpleDateFormat;

@RestController
@RequestMapping("/api")
public class MonthController {
	
	private final MonthRepository monthRepository;
	
	@Autowired
	public MonthController(MonthRepository monthRepository) {
        this.monthRepository = monthRepository;
    }
	
	@PostMapping("/createmonth")
	public ResponseEntity<String> createMonth(@RequestBody Month monthRequest) {
	    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	    String username = ((UserDetails) principal).getUsername();

	    String monthYear = monthRequest.getMonthYear();

	    Month monthObject = new Month(username, monthYear);
	    monthRepository.save(monthObject);

	    return new ResponseEntity<>("Month record created successfully", HttpStatus.CREATED);
	}
}
