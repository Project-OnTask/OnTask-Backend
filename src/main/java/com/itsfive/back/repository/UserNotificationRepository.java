package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.itsfive.back.model.User;
import com.itsfive.back.model.UserNotification;

public interface UserNotificationRepository extends CrudRepository<UserNotification, Long> {
	List<UserNotification> findByUserAndSeen(User user,boolean seen);
}
