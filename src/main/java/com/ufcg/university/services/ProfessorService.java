package com.ufcg.university.services;

import java.util.List;
import java.util.Optional;
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
	
	public ProfessorDTO getProfessorById(Long id) throws Exception {
		Optional<Professor> professor = this.professorRepository.findById(id);
		if (professor.isEmpty()) {
			throw new Exception("Professor Not Found");
		} else {
			return this.professorMapper.convertToProfessorDTO(professor.get());
		}
	}
	
	public Professor createProfessor(ProfessorDTO professorDTO) {
		Professor professor = this.professorMapper.convertFromProfessorDTO(professorDTO);
		return this.professorRepository.save(professor);
	}
	
	public ProfessorDTO updateProfessor(Long id, ProfessorDTO professorDTO) throws Exception {
		Optional<Professor> professor = this.professorRepository.findById(id);
		if (professor.isEmpty()) {
			throw new Exception("Professor Not Found");
		} else {
			Professor professorUpdated = professor.get();
			professorUpdated.setName(professorDTO.getName());
			professorUpdated.setDiscipline(professorDTO.getDiscipline());
			professorUpdated.setServiceTime(professorDTO.getServiceTime());
			this.professorRepository.save(professorUpdated);
			return professorDTO;
		}
	}
	
	public ProfessorDTO deleteProfessor(Long id) throws Exception {
		Optional<Professor> professor = this.professorRepository.findById(id);
		if (professor.isEmpty()) {
			throw new Exception("Professor Not Found");
		} else {
			this.professorRepository.deleteById(id);
			return this.professorMapper.convertToProfessorDTO(professor.get());
		}
	}
}
