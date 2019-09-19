package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.User;
import com.itsfive.back.model.UserEducation;

@Repository
public interface UserEducationRepository extends CrudRepository<UserEducation, Long>{
	public List<UserEducation> findByUser(User user);
}
