package com.itsfive.back.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.Group;
import com.itsfive.back.model.Notice;

@Repository
public interface NoticeRepository extends CrudRepository<Notice, Long>{
	public List<Notice> findAllByGroupId(Long groupId);
}
