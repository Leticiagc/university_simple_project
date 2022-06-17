package com.ufcg.university.dto;

public class UserLoginDTO {
	
	private String name;
	
	private String password;
	
	public UserLoginDTO() {}

	public UserLoginDTO(String name, String password) {
		this.name = name;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
