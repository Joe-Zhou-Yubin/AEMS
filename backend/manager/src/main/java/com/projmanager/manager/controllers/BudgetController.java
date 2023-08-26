package com.projmanager.manager.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.projmanager.manager.models.Budget;
import com.projmanager.manager.models.Expenditure;
import com.projmanager.manager.repository.BudgetRepository;
import com.projmanager.manager.repository.ExpenditureRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class BudgetController {
	private final BudgetRepository budgetRepository;

    @Autowired
    public BudgetController(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }
    
    @PostMapping("/createbudget/{monthId}")
    public ResponseEntity<String> createBudget(
            @PathVariable Long monthId,
            @RequestBody Budget budgetRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        String category = budgetRequest.getCategory();
        double budgetAmount = budgetRequest.getBudget();
        Date date = budgetRequest.getDate();

        // Create a new Income object
        Budget budget = new Budget(username, monthId, category, budgetAmount, date);

        // Save the Income object to the database
        budgetRepository.save(budget);

        return new ResponseEntity<>("Budget record created successfully", HttpStatus.CREATED);
    }
    
    @GetMapping("/getbudget/{monthid}/{category}")
    public ResponseEntity<List<Budget>> getBudgetByCategory(@PathVariable Long monthid, @PathVariable String category) {
        List<Budget> budgetList = budgetRepository.findByMonthidAndCategory(monthid, category);
        
        if (budgetList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(budgetList, HttpStatus.OK);
    }
    
    @PutMapping("/updatebudget/{budgetId}")
    public ResponseEntity<String> updateBudgetAmount(
            @PathVariable Long budgetId,
            @RequestBody Double newBudgetAmount) {
        // Check if the budget record exists
        if (!budgetRepository.existsById(budgetId)) {
            return new ResponseEntity<>("Budget record not found", HttpStatus.NOT_FOUND);
        }

        Budget budget = budgetRepository.findById(budgetId).orElse(null);
        if (budget != null) {
            budget.setBudget(newBudgetAmount);
            budgetRepository.save(budget);
            return new ResponseEntity<>("Budget amount updated successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update budget amount", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
