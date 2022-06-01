package com.ufcg.university.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Professor {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "serviceTime")
	private Integer serviceTime;
	
	@Column(name = "discipline")
	private String discipline;
	
	public Professor() {}
	
	public Professor(String name, Integer serviceTime, String discipline) {
		this.name = name;
		this.serviceTime = serviceTime;
		this.discipline = discipline;
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
}
