package com.ufcg.university.entities;

public class User {
	
	private String cpf;
	private String password;
	
	public User() {}
	
	public User(String cpf, String password) {
		super();
		this.cpf = cpf;
		this.password = password;
	}

	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}
