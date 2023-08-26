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
@Table(name = "savings")
public class Savings {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column (name="monthid")
    private Long monthid;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "savings")
    private double savings;

	public Savings() {
	}

	public Savings(Long monthid, String username, double savings) {
		super();
		this.monthid = monthid;
		this.username = username;
		this.savings = savings;
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

	public double getSavings() {
		return savings;
	}

	public void setSavings(double savings) {
		this.savings = savings;
	}
	
	

    
}
