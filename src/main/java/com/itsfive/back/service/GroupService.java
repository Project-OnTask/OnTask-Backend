package com.itsfive.back.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.model.Group;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class GroupService {
	@Autowired
	private GroupRepository groupRepository;
	
	//create group
	public Group createGroup(Group group) {
		Group t = groupRepository.save(group);
		return t;
	}
	
    //delete group
	public void deleteGroupById(Long id) {
		groupRepository.deleteById(id);
	}
	
    //edit group name
	public void editGroupName(Long id,String name) {
		Group group = new Group();
		group.setId(id);
		group.setName(name);
		groupRepository.save(group);
	}
	
    //edit group description
	private void editGroupDescription(Long id,String description) {
		Group group = new Group();
		group.setId(id);
		group.setDescription(description);
		groupRepository.save(group);
	}
	
    //get all groups in which a user is a member
	public List<Group> getGroupsByUser(Long id){
		return groupRepository.findGroupById(id);
	}	
    
    //edit group cover photo
	public String getCoverURL(Long id){
		return groupRepository.findById(id).get().getCoverPhoto();
	}
	
    //getGroupById
	private Optional<Group> getGroupById(Long id) {
		return groupRepository.findById(id);
	}
	
	public Optional<Group> getGroup(Long id) {
		return groupRepository.findById(id);
	}
}
