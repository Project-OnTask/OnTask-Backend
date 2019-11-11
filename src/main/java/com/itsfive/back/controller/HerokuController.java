package com.itsfive.back.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HerokuController {
	
	@GetMapping("/ping")
	public String authenticateUser() {
		return "ping";
	}
}
