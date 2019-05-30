package com.itsfive.back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{
	  private String email;
	
	  public UserNotFoundException( String email) {
	        super(String.format("No user found for email address '%s'", email));
	        this.email = email;
	    }
}
