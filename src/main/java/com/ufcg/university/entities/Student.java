package com.ufcg.university.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "registration")
	private String registration;
	
	public Student() {}
	
	public Student(String name, String password, String registration) {
		this.password = password;
		this.name = name;
		this.registration = registration;
	}
	
	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRegistration() {
		return registration;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRegistration(String registration) {
		this.registration = registration;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
