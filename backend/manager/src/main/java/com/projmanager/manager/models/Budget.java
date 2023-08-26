package com.projmanager.manager.models;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
@Table(name = "budget")
public class Budget {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column (name="monthid")
    private Long monthid;
    
    @Column(name = "username")
    private String username;

    @Column(name = "category")
    private String category;

    @Column(name = "budget")
    private double budget;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;

	public Budget() {
	}
	
	

	public Budget(String username, Long monthid,String category, double budget, Date date) {
        this.username = username;
        this.monthid = monthid;
        this.category = category;
        this.budget = budget;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getBudget() {
		return budget;
	}

	public void setBudget(double budget) {
		this.budget = budget;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
    
	
    
}
