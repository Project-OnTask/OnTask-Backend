package com.itsfive.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.itsfive.back.exception.BadRequestException;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.Task;
import com.itsfive.back.payload.CreateTaskRequest;
import com.itsfive.back.payload.EditTaskDescRequest;
import com.itsfive.back.payload.UploadFileResponse;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.service.FileService;
import com.itsfive.back.service.GroupMemberService;
import com.itsfive.back.service.TaskService;

@RestController
@RequestMapping("/api")
public class TaskController {
	@Autowired
	private TaskService taskService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private  TaskRepository taskRepository;
	
	@Autowired
	private GroupMemberService groupMemberService;
	
    @PostMapping("/tasks")
    public void createTask(@RequestBody CreateTaskRequest createTaskRequest) {
    	boolean isAdmin = groupMemberService.isMemberAnAdmin(createTaskRequest.getCreatedBy(),createTaskRequest.getGroupId());
    	
    	    	if(!isAdmin) {
    		throw new BadRequestException("Operation not allowed");
    	}
    	taskService.createTaskForGroup(createTaskRequest);
    }
    
    @GetMapping("/{group}/tasks")
    public List<Task> getTasksOfGroup(@PathVariable Long group){
    	return taskService.getAllTasksOfGroup(group);
    }
    
    @GetMapping("/tasks/{id}")
    public Task getTaskById(@PathVariable Long id){
    	return taskService.getTaskById(id);
    }
    
    @RequestMapping(value = "/tasks/{id}", method = RequestMethod.DELETE)
    public void deleteTask(@PathVariable Long id) {
    	taskRepository.deleteById(id);
    }
    
    @PostMapping("/tasks/edit-desc")
    public void editTaskDescription(@RequestBody EditTaskDescRequest Req) {
    	taskService.editTaskDescription(Req.getTaskId(), Req.getDescription());
    }
 
}
