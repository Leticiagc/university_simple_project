package com.ufcg.university.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;

public class ProfessorMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public ProfessorDTO convertToProfessorDTO(Professor professor) {
		this.modelMapper = new ModelMapper();
		ProfessorDTO professorDTO = this.modelMapper.map(professor, ProfessorDTO.class);
		return professorDTO;
	}
	
	public Professor convertFromProfessorDTO(ProfessorDTO professorDTO) {
		this.modelMapper = new ModelMapper();
		Professor professor = this.modelMapper.map(professorDTO, Professor.class);
		return professor;
	}

}
