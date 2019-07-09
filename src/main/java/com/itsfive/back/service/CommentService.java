package com.itsfive.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itsfive.back.payload.GetCommentResponse;
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
	
	public List<GetCommentResponse> getCommentsByTask(long taskId) {
		List<GetCommentResponse> fin = new ArrayList<>();
		List<Comment> cmnts = commentRepository.findAllByTaskId(taskId);
		for (int i = 0; i < cmnts.size(); i++) {
			GetCommentResponse obj = new GetCommentResponse(
					cmnts.get(i).getId(),
					cmnts.get(i).getCreatedBy().getUsername(),
					cmnts.get(i).getCreatedAt(),
					cmnts.get(i).getContent());
			fin.add(obj);
		}
		return fin;
	}
	
	public void postComment(PostCommentRequest postReq) {
		User user = userRepository.findById(postReq.getUserId()).get();
		Task task = taskRepository.findById(postReq.getTaskId()).get();
		Comment comment = new Comment(user,task,postReq.getContent());
		commentRepository.save(comment);
	}
}
