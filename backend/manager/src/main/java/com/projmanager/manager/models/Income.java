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
@Table(name = "income")
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name="monthid")
    private Long monthid;
    
    @Column(name = "username")
    private String username;

    @Column(name = "category")
    private String category;

    @Column(name = "income")
    private double income;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;


    // Default constructor
    public Income() {
    }

    // Parameterized constructor
    public Income(String username, Long monthid,String category, double income, Date date) {
        this.username = username;
        this.monthid = monthid;
        this.category = category;
        this.income = income;
        this.date = date;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getMonthid() {
		return monthid;
	}

	public void setMonthid(Long monthid) {
		this.monthid = monthid;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    

}
