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
@Table(name = "expenditure")
public class Expenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column (name="monthid")
    private Long monthid;
    
    @Column(name = "username")
    private String username;

    @Column(name = "category")
    private String category;

    @Column(name = "expenditure")
    private double expenditure;

    @Column(name = "date")
    @Temporal(TemporalType.DATE)
    private Date date;


    // Default constructor
    public Expenditure() {
    }

    // Parameterized constructor
    public Expenditure(String username, Long monthid,String category, double expenditure, Date date) {
        this.username = username;
        this.monthid = monthid;
        this.category = category;
        this.expenditure = expenditure;
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

	public double getExpenditure() {
		return expenditure;
	}

	public void setExpenditure(double expenditure) {
		this.expenditure = expenditure;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

    

}
