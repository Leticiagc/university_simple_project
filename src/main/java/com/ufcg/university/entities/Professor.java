package com.ufcg.university.entities;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
@Schema(name = "Objeto Professor")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Schema(
			title = "ID único",
			description = "Gerado automaticamente.",
			accessMode = Schema.AccessMode.READ_ONLY)
	private Long id;
	
	@Column(name = "name")
	@Schema(title = "Nome",
			example = "Francisco",
			required = true)
	private String name;
	
	@Column(name = "password")
	@Schema(title = "Senha",
			example = "1234Abcd",
			required = true,
			minLength = 8,
			maxLength = 16,
			description = "Senha de acesso.",
			format = "[0-9A-Za-z]")
	private String password;
	
	@Column(name = "serviceTime")
	@Schema(title = "Tempo de Serviço",
			example = "10",
			minimum = "0",
			exclusiveMinimum = true)
	private Integer serviceTime;
	
	@Column(name = "discipline")
	@Schema(title = "Disciplina",
			example = "Cálculo 1",
			nullable = true)
	private String discipline;
	
	public Professor() {}
	
	public Professor(String name, String password, Integer serviceTime, String discipline) {
		this.name = name;
		this.password = password;
		this.serviceTime = serviceTime;
		this.discipline = discipline;
	}

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}
	
	public Integer getServiceTime() {
		return serviceTime;
	}

	public String getDiscipline() {
		return discipline;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setServiceTime(Integer serviceTime) {
		this.serviceTime = serviceTime;
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
