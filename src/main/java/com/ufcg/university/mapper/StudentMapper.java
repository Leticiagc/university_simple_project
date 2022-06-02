package com.ufcg.university.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;

public class StudentMapper {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public StudentDTO convertToStudentDTO(Student student) {
		this.modelMapper = new ModelMapper();
		StudentDTO studentDTO = this.modelMapper.map(student, StudentDTO.class);
		return studentDTO;
	}
	
	public Student convertFromStudentDTO(StudentDTO studentDTO) {
		this.modelMapper = new ModelMapper();
		Student student = this.modelMapper.map(studentDTO, Student.class);
		return student;
	}

}
