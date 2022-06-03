package com.ufcg.university.dto;

public class StudentDTO {

	private String name;
	
	private String password;
	
	private String registration;
	
	public StudentDTO() {}

	public StudentDTO(String name, String password, String registration) {
		super();
		this.name = name;
		this.password = password;
		this.registration = registration;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRegistration() {
		return registration;
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
