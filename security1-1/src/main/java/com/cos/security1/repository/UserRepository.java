package com.cos.security1.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.security1.config.auth.PrincipalDetails;
import com.cos.security1.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
	// findBy 까지는 규칙, Username의 부분은 문법
	// select * from user where username = 1?으로 호출
	public User findByUsername(String username);
}
