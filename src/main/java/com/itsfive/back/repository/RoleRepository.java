package com.itsfive.back.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.itsfive.back.model.RoleName;
import com.itsfive.back.model.Roles;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
	 Optional<Roles> findByName(RoleName roleName);
}
