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

	public UserInput() {
	}

	public UserInput(Long monthid, String username, String userinput) {
		this.monthid = monthid;
		this.username = username;
		this.userinput = userinput;
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
    
	
    
}
