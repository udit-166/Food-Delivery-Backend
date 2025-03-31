package com.auth.user.core.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.auth.user.core.entity.User;


@Repository
public interface UserRepositories extends JpaRepository<User, UUID>{
	
	User findByEmail(String emailId);
	User findByPhone(String phone);
	User findByGoogleId(String googleId);
}
