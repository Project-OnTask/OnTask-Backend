package com.itsfive.back.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.exception.UserNotFoundException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.GroupMember;
import com.itsfive.back.model.GroupMembersKey;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.CreateGroupRequest;
import com.itsfive.back.payload.GetAllGroupsResponse;
import com.itsfive.back.payload.LoginRequest;
import com.itsfive.back.payload.UploadFileResponse;
import com.itsfive.back.repository.GroupActivityRepository;
import com.itsfive.back.repository.GroupMemberRepository;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.service.FileService;
import com.itsfive.back.service.GroupActivityService;
import com.itsfive.back.service.GroupMemberService;
import com.itsfive.back.service.GroupService;
import com.itsfive.back.service.MailSenderService;
import com.itsfive.back.service.UserService;

@RestController
@RequestMapping("/api")
public class GroupController {
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private GroupActivityRepository groupActivityRepository;
	
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private FileService fileService;
	
    @Autowired
    private MailSenderService senderService;
	
	//create group
    @PostMapping("/groups")
    public void createGroup(@RequestBody CreateGroupRequest createGroupRequest) {
    	Optional<User> createdBy = userService.getUserById(createGroupRequest.getUserId());
    	if(!createdBy.isPresent()) {
    		throw new BadRequestException("There is no user for provided id");
    	}
    	Group group = new Group(createGroupRequest.getName(),createGroupRequest.getDescription(),createdBy.get());
        Long groupId = groupService.createGroup(group,createdBy.get()).getId();   
    	GroupMembersKey AdminKey = new GroupMembersKey(createdBy.get().getId(),groupId);
    	GroupMember Admin = new GroupMember(AdminKey); 	
    	Admin.setRole("admin");
    	groupMemberService.addAdmin(Admin);
        if(createGroupRequest.getMembers() != null) {
        	for(int i=0;i<createGroupRequest.getMembers().length;i++) { 
            	GroupMembersKey key = new GroupMembersKey(createGroupRequest.getMembers()[i],group.getId());
            	GroupMember member = new GroupMember(key);
            	member.setRole("member");
            	groupMemberService.addMemberAtGroupInit(member);
            }
        }	
    }
    
    @GetMapping("/{user}/groups")
    public List<GetAllGroupsResponse> getGroups(@PathVariable Long user) {
    	List<GroupMember> memRecords = groupMemberService.getGroupsByMember(user);
    	List<GetAllGroupsResponse> res = new ArrayList<>();
    	if(memRecords != null) {
    		for(int i=0;i<memRecords.size();i++) {
    			GetAllGroupsResponse group = new GetAllGroupsResponse(
    					memRecords.get(i).getId().getGroupId(),
    					memRecords.get(i).getGroup().getName(),
    					memRecords.get(i).getRole()
    			);
    			res.add(group);
    		}
    	}
    	return res;
    }
    
    @GetMapping("/exists/group/{groupId}")
    public Group isGroup(@PathVariable Long groupId) {
    	if(groupService.getGroup(groupId) == null) {
    		throw new BadRequestException("Bad request");
    	}
    	return groupService.getGroup(groupId).get();
    }
    
    @PostMapping("/group/{groupId}/{userId}/change-cover")
    public void changeGroupCoverPhoto(@RequestParam("file") MultipartFile file,@PathVariable Long groupId,@PathVariable Long userId) {
    	if(!groupMemberService.isMemberAnAdmin(userId, groupId)) {
    		throw new BadRequestException("This operation requires priviledge elevation");
    	}
    	String fileName = fileService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                 .path("/api/downloadFile/")
                 .path(fileName)
                 .toUriString();

         UploadFileResponse response =  new UploadFileResponse(fileName, fileDownloadUri,
                file.getContentType(), file.getSize());
         Group group = groupService.getGroup(groupId).get();
         group.setCoverPhoto(response.getFileDownloadUri());
         groupRepository.save(group);
    }
    
    @PostMapping("/groups/{groupId}/edit-desc")
    public void editGroupDescription(@PathVariable long groupId,@RequestParam("editedBy") Long editedBy,@RequestParam("desc") String description) {	
    	groupService.editGroupDescription(groupId,editedBy,description);
    }
    
    @GetMapping("/groups/{groupId}")
    public Group getGroup(@PathVariable long groupId) {
    	return groupRepository.findById(groupId).get();
    }
    
    @GetMapping("/groups/{groupId}/activity")
    public List<GroupActivity> getGroupActivity(@PathVariable long groupId) { 
    	return groupActivityRepository.findByGroupId(groupId);
    }
    
    @GetMapping("/groups/{groupId}/itoken")
    public List<GroupActivity> getGroupInvitationToken(@PathVariable long groupId) { 
    	return groupActivityRepository.findByGroupId(groupId);
    }
    
}
