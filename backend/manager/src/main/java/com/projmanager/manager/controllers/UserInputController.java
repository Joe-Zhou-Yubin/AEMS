package com.projmanager.manager.controllers;

import java.io.Console;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.projmanager.manager.dto.CompletionRequest;
import com.projmanager.manager.dto.CompletionResponse;
import com.projmanager.manager.dto.OpenAiApiClient;
import com.projmanager.manager.models.UserInput;
import com.projmanager.manager.repository.ExpenditureRepository;
import com.projmanager.manager.repository.IncomeRepository;
import com.projmanager.manager.repository.UserinputRepository;
import com.projmanager.manager.models.Income;
import com.projmanager.manager.models.Expenditure;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class UserInputController {
	private final UserinputRepository userinputRepository;
	private final IncomeRepository incomeRepository;
	private final ExpenditureRepository expenditureRepository;
	private final OpenAiApiClient openAiApiClient;
    private final ObjectMapper objectMapper;
    
    @Autowired
    public UserInputController(OpenAiApiClient openAiApiClient, ObjectMapper objectMapper, UserinputRepository userinputRepository, IncomeRepository incomeRepository, ExpenditureRepository expenditureRepository) {
    	this.openAiApiClient = openAiApiClient;
        this.objectMapper = objectMapper;
    	this.userinputRepository = userinputRepository;
    	this.incomeRepository = incomeRepository;
    	this.expenditureRepository = expenditureRepository;
    }
    
    @PostMapping("/createinput/{monthId}")
    public ResponseEntity<String> createUserInput(
            @PathVariable Long monthId,
            @RequestBody UserInput inputRequest) throws IOException, InterruptedException, ParseException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();

        // Extract data from the request body
        String userinput = inputRequest.getUserinput(); // this is user prompt

        
     // Add the preset prompts
        List<String> presetPrompts = new ArrayList<>();
        presetPrompts.add("extract information from the following user input:");
        presetPrompts.add("type of transaction (either income or expenditure)\r\n"
        		+ "\r\n"
        		+ "for category of income (savings, owed money)\r\n"
        		+ "for category of expenditure (food, transport, utilities etc)\r\n"
        		+ "summary description of the transaction (person, place as applicable)\r\n"
        		+ "amount spent\r\n"
        		+ "date of spending");
        presetPrompts.add("and parse the information back to me only in json format(datatype String type (type of transaction), datatype String category, datatype String description, datatype Double amount, datatype String date)");
        presetPrompts.add("Context: today is 26-8-23");
        presetPrompts.add("user input:");
        // Select the GPT-3 model service
        OpenAiApiClient.OpenAiService service = OpenAiApiClient.OpenAiService.GPT_3;
        
     // Combine the prompts (previous answer, preset prompts, and chunk prompt)
        List<String> combinedPrompts = new ArrayList<>();
        
        combinedPrompts.addAll(presetPrompts);
        combinedPrompts.add(userinput);

        String combinedPrompt = String.join("\n\n", combinedPrompts);

        // Create a completion request with the combined prompt
        CompletionRequest completionRequest = CompletionRequest.defaultWith(combinedPrompt);
        String requestBodyAsJson = objectMapper.writeValueAsString(completionRequest);

        // Send request to OpenAI API and get the response JSON
        String responseJson = openAiApiClient.postToOpenAiApi(requestBodyAsJson, service);

        // Parse the JSON response using ObjectMapper
        CompletionResponse completionResponse = objectMapper.readValue(responseJson, CompletionResponse.class);

        // Get the answer from the response
        String answer = completionResponse.firstAnswer().orElse("");

        // Print the response from ChatGPT
        System.out.println("Response from ChatGPT:");
        System.out.println(answer);
        
        JsonNode jsonResponse = objectMapper.readTree(answer);
        
     // Extract fields from the JSON response
        String type = jsonResponse.get("type").asText();
        String category = jsonResponse.get("category").asText();
        String description = jsonResponse.get("description").asText();
        double amount = jsonResponse.get("amount").asDouble();
        String dateString = jsonResponse.get("date").asText();

     // Convert the date string to a Date object
     Date date = new SimpleDateFormat("dd-MM-yy").parse(dateString);
        
     // Determine if it's income or expenditure
        if ("income".equalsIgnoreCase(type)) {
            // Create a new Income object
            Income income = new Income(monthId, username, category, amount, date);
            // Save the Income object to the database
            incomeRepository.save(income);
        } else if ("expenditure".equalsIgnoreCase(type)) {
            // Create a new Expenditure object
            Expenditure expenditure = new Expenditure(monthId, username, category, amount, date);
            // Save the Expenditure object to the database
            expenditureRepository.save(expenditure);
        }
        
        // Create a new UserInput object
        UserInput userInputFromResponse = new UserInput(monthId, username, userinput, type, category, description, amount, dateString);

        // Save the UserInput object to the database
        userinputRepository.save(userInputFromResponse);
        
        return new ResponseEntity<>("User Input record created successfully", HttpStatus.CREATED);
    }
    
    @DeleteMapping("/deleteinput/{userinputId}")
    public ResponseEntity<String> deleteUserInput(@PathVariable Long userinputId) {
        // Check if the user input record exists
        if (!userinputRepository.existsById(userinputId)) {
            return new ResponseEntity<>("User input record not found", HttpStatus.NOT_FOUND);
        }

        // Delete the user input record
        userinputRepository.deleteById(userinputId);

        return new ResponseEntity<>("User input record deleted successfully", HttpStatus.OK);
    }

}
