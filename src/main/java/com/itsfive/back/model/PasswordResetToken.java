package com.itsfive.back.model;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class PasswordResetToken {
  
    private static final int EXPIRATION = 60 * 60 * 24;
  
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
  
    private String token;
  
    @OneToOne(targetEntity = User.class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
  
    private Date expiryDate;
    
    public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public PasswordResetToken(User user,String token) {
    	this.user = user;
    	this.token = token;
    	Calendar calendar = Calendar.getInstance(); // gets a calendar using the default time zone and locale.
    	calendar.add(Calendar.SECOND, EXPIRATION);
    	this.expiryDate = calendar.getTime();
    }
	
	public PasswordResetToken() {
		
	}
    
    public void setToken(String token) {
    	this.token = token;
    }
    
    public String getToken() {
    	return token;
    }
    
}
