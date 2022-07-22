package com.ufcg.university.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Modelo ProfessorDTO", description = "Modelo de ProfessorDTO da aplicação.")
public class ProfessorDTO {

	@Schema(
			title = "CPF",
			description = "CPF do professor",
			example = "111222333-00",
			required = true
	)
	private String cpf;

	@Schema(
			title = "Senha",
			description = "Senha",
			example = "1234Abcd",
			required = true,
			minLength = 8,
			maxLength = 16,
			format = "regex[0-9A-Za-z]"
	)
	private String password;

	@Schema(
			title = "Tempo de Serviço",
			example = "15",
			minimum = "0",
			maximum = "70",
			exclusiveMinimum = true
	)
	private Integer serviceTime;

	@Schema(
			title = "Disciplina",
			example = "Lógica",
			nullable = true,
			allowableValues = "Cálculo 1, P1, LP1, EDA, Lógica"
	)
	private String discipline;
	
	public ProfessorDTO() {}

	public ProfessorDTO(String cpf, String password, Integer serviceTime, String discipline) {
		this.cpf = cpf;
		this.password = password;
		this.serviceTime = serviceTime;
		this.discipline = discipline;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Integer getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
