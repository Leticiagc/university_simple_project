package com.ufcg.university.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import com.ufcg.university.mapper.StudentMapper;
import com.ufcg.university.repositories.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private StudentMapper studentMapper;
	
	public List<StudentDTO> listStudents() {
		List<StudentDTO> students = this.studentRepository
				.findAll().stream().map(this.studentMapper::convertToStudentDTO)
				.collect(Collectors.toList());
		return students;
	}
	
	public StudentDTO getStudentById(Long id) throws Exception {
		Optional<Student> student = this.studentRepository.findById(id);
		if (student.isEmpty()) {
			throw new Exception("Student Not Found");
		} else {
			return this.studentMapper.convertToStudentDTO(student.get());
		}
	}
	
	public Student createStudent(StudentDTO studentDTO) {
		Student student = this.studentMapper.convertFromStudentDTO(studentDTO);
		return this.studentRepository.save(student);
	}
	
	public StudentDTO updateStudent(Long id, StudentDTO studentDTO) throws Exception {
		Optional<Student> student = this.studentRepository.findById(id);
		if (student.isEmpty()) {
			throw new Exception("Student Not Found");
		} else {
			Student studentUpdated = student.get();
			studentUpdated.setName(studentDTO.getName());
			studentUpdated.setRegistration(studentDTO.getRegistration());
			this.studentRepository.save(studentUpdated);
			return studentDTO;
		}
	}
	
	public StudentDTO deleteStudent(Long id) throws Exception {
		Optional<Student> student = this.studentRepository.findById(id);
		if (student.isEmpty()) {
			throw new Exception("Professor Not Found");
		} else {
			this.studentRepository.deleteById(id);
			return this.studentMapper.convertToStudentDTO(student.get());
		}
	}
}