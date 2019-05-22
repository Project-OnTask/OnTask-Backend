package com.itsfive.back.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itsfive.back.model.Group;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.LoginRequest;
import com.itsfive.back.service.GroupService;
import com.itsfive.back.service.UserService;

@RestController
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private UserService userService;
	
    @PostMapping("/users/{id}/groups")
    public boolean createGroup(@RequestBody Group group,@PathVariable Long id) {
    	User user = userService.getUserById(id).orElse(null);
    	boolean g = false;
    	
    	if(user != null) {
    		g = true;
    		group.setCreatedBy(user);
        	groupService.createGroup(group);
    	}
    	return g;
    }
    
    @GetMapping("/groups/{id}")
    public boolean deleteGroup(@RequestBody Group group,@PathVariable Long id) {
    	//check if user is the admin of the group
    	return false;
    }
}
