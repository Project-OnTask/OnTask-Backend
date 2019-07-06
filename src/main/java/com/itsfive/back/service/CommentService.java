package com.itsfive.back.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.payload.PostCommentRequest;
import com.itsfive.back.repository.CommentRepository;
import com.itsfive.back.repository.UserRepository;
import com.itsfive.back.repository.TaskRepository;
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
	
	public List<Comment> getCommentsByTask(long taskId) {
		return commentRepository.findAllByTaskId(taskId);
	}
	
	public void postComment(PostCommentRequest postReq) {
		User user = userRepository.findById(postReq.getUserId()).get();
		Task task = taskRepository.findById(postReq.getTaskId()).get();
		Comment comment = new Comment(user,task,postReq.getContent());
		commentRepository.save(comment);
	}
}
