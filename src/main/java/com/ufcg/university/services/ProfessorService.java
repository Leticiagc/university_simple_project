package com.ufcg.university.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.mapper.ProfessorMapper;
import com.ufcg.university.repositories.ProfessorRepository;

@Service
public class ProfessorService {
	
	@Autowired
	private ProfessorRepository professorRepository;
	
	@Autowired
	private ProfessorMapper professorMapper;
	
	public List<ProfessorDTO> listProfessors() {
		List<Professor> professors = this.professorRepository.findAll();
		List<ProfessorDTO> professorsDTO = professors.stream()
				.map(this.professorMapper::convertToProfessorDTO)
				.collect(Collectors.toList());
		return professorsDTO;
	}
}
