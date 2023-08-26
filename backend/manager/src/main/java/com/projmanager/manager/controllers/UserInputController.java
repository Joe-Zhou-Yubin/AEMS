package com.projmanager.manager.controllers;

import java.io.Console;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanager.manager.models.Income;
import com.projmanager.manager.models.UserInput;
import com.projmanager.manager.repository.IncomeRepository;
import com.projmanager.manager.repository.UserinputRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserInputController {
	private final UserinputRepository userinputRepository;

    @Autowired
    public UserInputController(UserinputRepository userinputRepository) {
        this.userinputRepository = userinputRepository;
    }
    
    @PostMapping("/createinput/{monthId}")
    public ResponseEntity<String> createUserInput(
            @PathVariable Long monthId,
            @RequestBody UserInput inputRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        String userinput = inputRequest.getUserinput(); // this is user prompt

        // Create a new Income object
        UserInput userInput2 = new UserInput(monthId, username, userinput);

        // Save the Income object to the database
        userinputRepository.save(userInput2);
        
        return new ResponseEntity<>("User Input record created successfully", HttpStatus.CREATED);
    }
}
