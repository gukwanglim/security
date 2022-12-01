package com.cos.security1.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class User {

	@Id        // Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY)       // 프로젝트에서 연결된  DB의 넘버링 전략을 따라간다.
	private int id;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private	String email;
	
	@Column
	private String role;
	
	@Column
	private String provider;
	
	@Column
	private String providerId;
	
	@CreationTimestamp
	private Timestamp createDate;

	@Builder
	public User(String username, String password, String email, String role, String provider, String providerId,
			Timestamp createDate) {
		this.username = username;
		this.password = password;
		this.email = email;
		this.role = role;
		this.provider = provider;
		this.providerId = providerId;
		this.createDate = createDate;
	}
	
	
	
}
