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
	
	@Column(name = "registration")
	private String registration;
	
	public Student() {}
	
	public Student(String name, String registration) {
		this.name = name;
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public String getRegistration() {
		return registration;
	}
}
