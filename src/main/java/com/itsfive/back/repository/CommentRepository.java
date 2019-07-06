package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.Comment;

@Repository
public interface CommentRepository extends CrudRepository<Comment, Long>{
	public List<Comment> findAllByTaskId(long taskId);
}
