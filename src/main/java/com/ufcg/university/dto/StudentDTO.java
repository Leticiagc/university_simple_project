package com.ufcg.university.dto;

public class StudentDTO {

	private String name;
	
	private String registration;
	
	public StudentDTO() {}

	public StudentDTO(String name, String registration) {
		super();
		this.name = name;
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
}
