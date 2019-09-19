package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.User;
import com.itsfive.back.model.UserEducation;
import com.itsfive.back.model.UserWork;

@Repository
public interface UserWorkRepository extends CrudRepository<UserWork, Long> {
	public List<UserWork> findByUser(User user);
}
