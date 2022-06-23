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
	
	@Column(name = "name")
	@Schema(
			title = "Nome",
			description = "Nome do Student",
			example = "Josué",
			required = true
	)
	private String name;
	
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
