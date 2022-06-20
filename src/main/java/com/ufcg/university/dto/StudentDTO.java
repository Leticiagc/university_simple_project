package com.ufcg.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Modelo StudentDTO", description = "Modelo de StudentDTO da aplicação.")
public class StudentDTO {

	@Schema(
			title = "Nome",
			description = "Nome do Estudante",
			example = "José",
			required = true
	)
	private String name;

	@Schema(
			title = "Senha",
			description = "Senha de acesso",
			example = "78adRf99",
			required = true,
			minLength = 8,
			maxLength = 24,
			format = "regex[0-9A-Za-z]"
	)
	private String password;

	@Schema(
			title = "Matricula",
			description = "Matrícula do Estudante",
			example = "118111693",
			maxLength = 9
	)
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
