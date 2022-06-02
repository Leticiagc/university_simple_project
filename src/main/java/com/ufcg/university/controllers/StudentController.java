package com.ufcg.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import com.ufcg.university.services.StudentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/student")
@Api(value = "Student")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "List All Students")
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		return new ResponseEntity<>(this.studentService.listStudents(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Create a Student")
	public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<>(this.studentService.createStudent(studentDTO), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Student By Id")
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(this.studentService.getStudentById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Student By Id")
	public ResponseEntity<?> deleteStudentById(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<>(this.studentService.deleteStudent(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Student")
	public ResponseEntity<?> updateStudentById(@RequestParam("id") Long id, @RequestBody StudentDTO studentDTO) {
		try {
			return new ResponseEntity<>(this.studentService.updateStudent(id, studentDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NO_CONTENT);
		}
	}

}
