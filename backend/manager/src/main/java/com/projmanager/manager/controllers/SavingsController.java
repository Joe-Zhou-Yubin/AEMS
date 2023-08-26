package com.projmanager.manager.controllers;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanager.manager.models.Expenditure;
import com.projmanager.manager.models.Income;
import com.projmanager.manager.models.Savings;
import com.projmanager.manager.repository.SavingsRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class SavingsController {
	private final SavingsRepository savingsRepository;

    @Autowired
    public SavingsController(SavingsRepository savingsRepository) {
        this.savingsRepository = savingsRepository;
    }
    
    @PostMapping("/createsavings/{monthId}")
    public ResponseEntity<String> createSavings(
            @PathVariable Long monthId,
            @RequestBody Savings savingsRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        double savingsAmount = savingsRequest.getSavings();

        // Create a new Income object
        Savings savings = new Savings(monthId, username, savingsAmount);

        // Save the Income object to the database
        savingsRepository.save(savings);

        return new ResponseEntity<>("Savings record created successfully", HttpStatus.CREATED);
    }
    
    @GetMapping("/getsavings/{savingsId}")
    public ResponseEntity<Savings> getSavingsById(@PathVariable Long savingsId) {
        Optional<Savings> savings = savingsRepository.findById(savingsId);
        
        if (savings.isPresent()) {
            return new ResponseEntity<>(savings.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @DeleteMapping("/deletesavings/{savingsId}")
    public ResponseEntity<String> deleteSavings(@PathVariable Long savingsId) {
        // Check if the savings record exists
        if (!savingsRepository.existsById(savingsId)) {
            return new ResponseEntity<>("Savings record not found", HttpStatus.NOT_FOUND);
        }

        // Delete the savings record
        savingsRepository.deleteById(savingsId);

        return new ResponseEntity<>("Savings record deleted successfully", HttpStatus.OK);
    }


    
    
}
