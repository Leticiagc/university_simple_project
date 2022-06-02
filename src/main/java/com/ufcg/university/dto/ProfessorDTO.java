package com.ufcg.university.dto;

public class ProfessorDTO {
	
	private String name;
	
	private Integer serviceTime;
	
	private String discipline;
	
	public ProfessorDTO() {}

	public ProfessorDTO(String name, Integer serviceTime, String discipline) {
		this.name = name;
		this.serviceTime = serviceTime;
		this.discipline = discipline;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
}
