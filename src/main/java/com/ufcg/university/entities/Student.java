package com.ufcg.university.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.*;

@Entity
@Table(name = "student")
@Schema(name = "Modelo Student", description = "Modelo de Student da aplicação.")
public class Student {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(
			title = "ID único",
			description = "Gerado automaticamente",
			accessMode = Schema.AccessMode.READ_ONLY,
			example = "2"
	)
	private Long id;
	
	@Column(name = "cpf", unique = true)
	@Schema(
			title = "Cpf",
			description = "Cpf do Student",
			example = "111222333-00",
			required = true
	)
	private String cpf;
	
	@Column(name = "password")
	@Schema(
			title = "Senha",
			description = "Senha de acesso",
			required = true,
			minLength = 8,
			maxLength = 24,
			format = "regex[0-9A-Za-z]"
	)
	private String password;
	
	@Column(name = "registration")
	@Schema(
			title = "Matricula",
			description = "Matricula do Student",
			example = "118111398",
			maxLength = 9
	)
	private String registration;
	
	public Student() {}
	
	public Student(String cpf, String password, String registration) {
		this.password = password;
		this.cpf = cpf;
		this.registration = registration;
	}
	
	public Long getId() {
		return id;
	}

	public String getCpf() {
		return cpf;
	}

	public String getRegistration() {
		return registration;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
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
