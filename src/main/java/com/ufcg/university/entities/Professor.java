package com.ufcg.university.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "professor")
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "password")
	private String password;
	
	@Column(name = "serviceTime")
	private Integer serviceTime;
	
	@Column(name = "discipline")
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
