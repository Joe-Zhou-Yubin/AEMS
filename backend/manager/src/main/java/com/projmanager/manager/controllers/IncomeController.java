package com.projmanager.manager.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

import com.projmanager.manager.models.Income;
import com.projmanager.manager.repository.IncomeRepository;

@RestController
@RequestMapping("/api")
public class IncomeController {

    private final IncomeRepository incomeRepository;

    @Autowired
    public IncomeController(IncomeRepository incomeRepository) {
        this.incomeRepository = incomeRepository;
    }

    @PostMapping("/createincome/{monthId}")
    public ResponseEntity<String> createIncome(
            @PathVariable Long monthId,
            @RequestBody Income incomeRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        String category = incomeRequest.getCategory();
        double incomeAmount = incomeRequest.getIncome();
        Date date = incomeRequest.getDate();

        // Create a new Income object
        Income income = new Income(username, monthId, category, incomeAmount, date);

        // Save the Income object to the database
        incomeRepository.save(income);

        return new ResponseEntity<>("Income record created successfully", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/deleteincome/{incomeId}")
    public ResponseEntity<String> deleteIncome(@PathVariable Long incomeId) {
        // Check if the income record exists
        if (!incomeRepository.existsById(incomeId)) {
            return new ResponseEntity<>("Income record not found", HttpStatus.NOT_FOUND);
        }

        // Delete the income record
        incomeRepository.deleteById(incomeId);

        return new ResponseEntity<>("Income record deleted successfully", HttpStatus.OK);
    }
    
    @GetMapping("/getincome/{monthid}")
    public ResponseEntity<List<Income>> getIncomeByMonth(@PathVariable Long monthid) {
        List<Income> incomeList = incomeRepository.findByMonthid(monthid);
        
        if (incomeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(incomeList, HttpStatus.OK);
    }

}
