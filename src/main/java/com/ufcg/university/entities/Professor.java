package com.ufcg.university.entities;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
@ApiModel(value = "Objeto Professor", description = "Modelo de Objetos do tipo Professor.")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@ApiModelProperty(value = "ID Único",
			example = "5",
			position = 0,
			allowableValues = "range[1,infinite)")
	private Long id;
	
	@Column(name = "name")
	@ApiModelProperty(value = "Nome",
			example = "Francisco",
			required = true,
			position = 1)
	private String name;
	
	@Column(name = "password")
	@ApiModelProperty(value = "Senha",
			example = "1234ABcd",
			required = true,
			position = 2)
	private String password;
	
	@Column(name = "serviceTime")
	@ApiModelProperty(value = "Tempo de Serviço",
			example = "5",
			allowableValues = "range[1, 50]",
			position = 3)
	private Integer serviceTime;
	
	@Column(name = "discipline")
	@ApiModelProperty(value = "Disciplina",
			example = "Cálculo 1",
			allowableValues = "Cálculo 1, P1, EDA, Lógica",
			position = 4)
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
