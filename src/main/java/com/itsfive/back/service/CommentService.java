package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.payload.GetCommentResponse;
import com.itsfive.back.payload.PostCommentRequest;
import com.itsfive.back.repository.CommentRepository;
import com.itsfive.back.repository.UserRepository;
import com.pusher.rest.Pusher;
import com.itsfive.back.repository.TaskRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itsfive.back.config.PusherConfig;
import com.itsfive.back.model.Comment;
import com.itsfive.back.model.Task;
import com.itsfive.back.model.User;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TaskRepository taskRepository;
	

	public List<GetCommentResponse> getCommentsByTask(long taskId) {
		List<GetCommentResponse> fin = new ArrayList<>();
		List<Comment> cmnts = commentRepository.findAllByTaskId(taskId);
		for (int i = 0; i < cmnts.size(); i++) {
			GetCommentResponse obj = new GetCommentResponse(
					cmnts.get(i).getId(),
					cmnts.get(i).getCreatedBy().getFName(),
					cmnts.get(i).getCreatedAt(),
					cmnts.get(i).getContent());
			fin.add(obj);
		}
		return fin;
	}
	
	public void postComment(PostCommentRequest postReq) throws JsonProcessingException {
	
		ObjectMapper objectMapper = new ObjectMapper();
		
		User user = userRepository.findById(postReq.getUserId()).get();
		Task task = taskRepository.findById(postReq.getTaskId()).get();
		Comment comment = new Comment(user,task,postReq.getContent());
		commentRepository.save(comment);
		
		GetCommentResponse obj = new GetCommentResponse(
				comment.getId(),
				comment.getCreatedBy().getFName(),
				comment.getCreatedAt(),
				comment.getContent()); 
		
		PusherConfig.setObj().trigger("chat_"+task.getId(), "new_comment",objectMapper.writeValueAsString(obj));
	}
}
