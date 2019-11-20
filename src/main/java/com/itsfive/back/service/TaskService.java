package com.itsfive.back.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.itsfive.back.model.Group;
import com.itsfive.back.model.GroupActivity;
import com.itsfive.back.model.UserTask;
import com.itsfive.back.model.User;
import com.itsfive.back.payload.CreateTaskRequest;
import com.itsfive.back.repository.GroupRepository;
import com.itsfive.back.repository.SubtaskRepository;
import com.itsfive.back.repository.TaskRepository;
import com.itsfive.back.repository.UserRepository;

@Service
public class TaskService {
	@Autowired
	private TaskRepository taskRepository;
	
	@Autowired
	private SubtaskRepository subtaskRepository;
	
	@Autowired
	private GroupMemberService groupMemberService;
	
	@Autowired
	private UserNotificationService userNotificationService;
	
	@Autowired
	private GroupActivityService groupActivityService;
	
	@Autowired
	private TaskActivityService taskActivityService;
	
	@Autowired
	private GroupRepository groupRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	ObjectMapper objectMapper = new ObjectMapper();
	JavaTimeModule module = new JavaTimeModule();
	
	public void createTaskForGroup(CreateTaskRequest createTaskRequest) throws JsonProcessingException {
		UserTask task = new UserTask(createTaskRequest.getName(),createTaskRequest.getDescription(),createTaskRequest.getStartDate(),createTaskRequest.getDueDate());
		Group group = groupRepository.findById(createTaskRequest.getGroupId()).get();
		task.setGroup(group);
		User user = userRepository.findById(createTaskRequest.getCreatedBy()).get();
		task.setCreatedBy(user);
		UserTask tk = taskRepository.save(task);
		String desc = "<b>"+user.getFName()+"</b> added a new task <b>"+task.getName()+"</b> in group <b>"+group.getName()+"</b>";
		GroupActivity gc = groupActivityService.addGroupActivity(group.getId(), user, desc);
		taskActivityService.addTaskActivity(tk.getId(), user, desc);
		userNotificationService.createUserNotificationsForGroupMembers(createTaskRequest.getGroupId(), gc);
	}
	
	public List<UserTask> getAllTasksOfGroup(Long groupId) {
		return taskRepository.findAllByGroupId(groupId);
	}
	
	public UserTask getTaskById(long id) {
		return taskRepository.findById(id).get();
	}
	
	public void editTaskDescription(long editedById,long taskId,String description) throws JsonProcessingException {
		UserTask task = taskRepository.findById(taskId).get();
		User editedBy = userRepository.findById(editedById).get();
		task.setDescription(description);
		taskRepository.save(task);
		String desc = "<b>"+editedBy.getFName()+"</b> edited task description";
		taskActivityService.addTaskActivity(taskId, editedBy, desc);
	}

	public boolean toggleTaskCompletedStatus(long userId,long taskId) {
		UserTask task = taskRepository.findById(taskId).get();
		boolean c_status = task.isCompleted() ? false : true;
		task.setCompleted(c_status);
		taskRepository.save(task);
		return c_status;
	}

	public void editTaskDueDate(long editedById,long taskId,Date NewDueDate) throws JsonProcessingException {  
		UserTask task = taskRepository.findById(taskId).get();
		User editedBy = userRepository.findById(editedById).get(); 
		task.setDueDate(NewDueDate); 
		taskRepository.save(task);
		String desc = "<b>"+editedBy.getFName()+"</b> edited task due date";
		taskActivityService.addTaskActivity(taskId, editedBy, desc);
	}

}

