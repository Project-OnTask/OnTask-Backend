package com.itsfive.back.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.itsfive.back.model.Comment;
import com.itsfive.back.payload.GetCommentResponse;
import com.itsfive.back.payload.PostCommentRequest;
import com.itsfive.back.service.CommentService;

@RestController
@RequestMapping("/api")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@PostMapping("/comments")
	public void addComment(@RequestBody PostCommentRequest postReq) throws JsonProcessingException {
		commentService.postComment(postReq);
	}
	
	@GetMapping("/comments/{taskId}")
	public List<GetCommentResponse> getComments(@PathVariable long taskId) {
		return commentService.getCommentsByTask(taskId);
	}
}
