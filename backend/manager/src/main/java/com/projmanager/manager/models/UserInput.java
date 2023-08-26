package com.projmanager.manager.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "userinput")
public class UserInput {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name="monthid")
    private Long monthid;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "userinput")
    private String userinput;
    
    @Column(name = "type")
    private String type;
    
    @Column(name = "category")
    private String category;
    
    @Column(name = "description")
    private String description;
    
    @Column(name = "amount")
    private Double amount;
    
    @Column(name = "date")
    private String date;
    
	public UserInput() {
	}

	

	public UserInput(Long monthid, String username, String userinput) {
		this.monthid = monthid;
		this.username = username;
		this.userinput = userinput;
	}



	public UserInput(Long monthid, String username, String userinput, String type, String category, String description,
			Double amount, String date) {
		this.monthid = monthid;
		this.username = username;
		this.userinput = userinput;
		this.type = type;
		this.category = category;
		this.description = description;
		this.amount = amount;
		this.date = date;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMonthid() {
		return monthid;
	}

	public void setMonthid(Long monthid) {
		this.monthid = monthid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserinput() {
		return userinput;
	}

	public void setUserinput(String userinput) {
		this.userinput = userinput;
	}



	public String getType() {
		return type;
	}



	public void setType(String type) {
		this.type = type;
	}



	public String getCategory() {
		return category;
	}



	public void setCategory(String category) {
		this.category = category;
	}



	public String getDescription() {
		return description;
	}



	public void setDescription(String description) {
		this.description = description;
	}



	public Double getAmount() {
		return amount;
	}



	public void setAmount(Double amount) {
		this.amount = amount;
	}



	public String getDate() {
		return date;
	}



	public void setDate(String date) {
		this.date = date;
	}
    
	
    
}
