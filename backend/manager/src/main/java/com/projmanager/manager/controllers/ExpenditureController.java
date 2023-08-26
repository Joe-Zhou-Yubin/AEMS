package com.projmanager.manager.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;

import com.projmanager.manager.models.Expenditure;
import com.projmanager.manager.repository.ExpenditureRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ExpenditureController {

    private final ExpenditureRepository expenditureRepository;

    @Autowired
    public ExpenditureController(ExpenditureRepository expenditureRepository) {
        this.expenditureRepository = expenditureRepository;
    }

    @PostMapping("/createexpenditure/{monthId}")
    public ResponseEntity<String> createExpenditure(
            @PathVariable Long monthId,
            @RequestBody Expenditure expenditureRequest) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        String category = expenditureRequest.getCategory();
        double expenditureAmount = expenditureRequest.getExpenditure();
        Date date = expenditureRequest.getDate();

        // Create a new Income object
        Expenditure expenditure = new Expenditure(username, monthId, category, expenditureAmount, date);

        // Save the Income object to the database
        expenditureRepository.save(expenditure);

        return new ResponseEntity<>("Expenditure record created successfully", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/deleteexpenditure/{expId}")
    public ResponseEntity<String> deleteExpenditure(@PathVariable Long expId) {
        // Check if the income record exists
        if (!expenditureRepository.existsById(expId)) {
            return new ResponseEntity<>("Expenditure record not found", HttpStatus.NOT_FOUND);
        }

        // Delete the income record
        expenditureRepository.deleteById(expId);

        return new ResponseEntity<>("Expenditure record deleted successfully", HttpStatus.OK);
    }
    
    @GetMapping("/getexpenditure/{monthid}")
    public ResponseEntity<List<Expenditure>> getExpenditureByMonth(@PathVariable Long monthid) {
        List<Expenditure> incomeList = expenditureRepository.findByMonthid(monthid);
        
        if (incomeList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(incomeList, HttpStatus.OK);
    }
    
    @GetMapping("/getexpenditurebycategory/{monthid}/{category}")
    public ResponseEntity<List<Expenditure>> getExpenditureByCategory(@PathVariable Long monthid, @PathVariable String category) {
        List<Expenditure> expenditureList = expenditureRepository.findByMonthidAndCategory(monthid, category);
        
        if (expenditureList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(expenditureList, HttpStatus.OK);
    }
    
    @GetMapping("/gettotalbycategory/{monthid}/{category}")
    public ResponseEntity<Double> getTotalExpenditureByMonthAndCategory(
            @PathVariable Long monthid,
            @PathVariable String category) {
        List<Expenditure> expenditureList = expenditureRepository.findByMonthidAndCategory(monthid, category);
        
        double totalExpenditure = expenditureList.stream()
                .mapToDouble(Expenditure::getExpenditure)
                .sum();

        return new ResponseEntity<>(totalExpenditure, HttpStatus.OK);
    }
    
    @GetMapping("/gettotalbymonth/{monthid}")
    public ResponseEntity<Double> getTotalExpenditureByMonth(@PathVariable Long monthid) {
        List<Expenditure> expenditureList = expenditureRepository.findByMonthid(monthid);

        double totalExpenditure = expenditureList.stream()
                .mapToDouble(Expenditure::getExpenditure)
                .sum();

        return new ResponseEntity<>(totalExpenditure, HttpStatus.OK);
    }



}
