package com.itsfive.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itsfive.back.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer>{

}
