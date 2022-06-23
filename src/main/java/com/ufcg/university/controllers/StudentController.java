package com.ufcg.university.controllers;

import java.util.List;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
import com.ufcg.university.services.StudentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/student")
@SecurityRequirement(name = "Authorization")
public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@Operation(
			summary = "Get All Students",
			description = "Student List",
			responses = {
				    @ApiResponse(responseCode = "200", description = "It's Ok"),
				    @ApiResponse(responseCode = "400", description = "Bad Request"),
				    @ApiResponse(responseCode = "403", description = "Forbidden")
			}
	)
	public ResponseEntity<List<StudentDTO>> getAllStudents() {
		return new ResponseEntity<>(this.studentService.listStudents(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@Operation(
			summary = "Get Student By Id",
			description = "Student Info",
			responses = {
				    @ApiResponse(responseCode = "200", description = "It's Ok"),
				    @ApiResponse(responseCode = "404", description = "Student Not Found"),
				    @ApiResponse(responseCode = "400", description = "Bad Request"),
				    @ApiResponse(responseCode = "403", description = "Forbidden")
			}
	)
	public ResponseEntity<?> getStudentById(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(this.studentService.getStudentById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@Operation(
			summary = "Delete Student By Id",
			description = "Delete a Student from the system",
			responses = {
				    @ApiResponse(responseCode = "200", description = "It's Ok"),
				    @ApiResponse(responseCode = "404", description = "Student Not Found"),
				    @ApiResponse(responseCode = "400", description = "Bad Request"),
				    @ApiResponse(responseCode = "403", description = "Forbidden")
			}
	)
	public ResponseEntity<?> deleteStudentById(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<>(this.studentService.deleteStudent(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@Operation(
			summary = "Update Student By Id",
			description = "Update a Student from the system",
			responses = {
				    @ApiResponse(responseCode = "200", description = "It's Ok"),
				    @ApiResponse(responseCode = "404", description = "Student Not Found"),
				    @ApiResponse(responseCode = "400", description = "Bad Request"),
				    @ApiResponse(responseCode = "403", description = "Forbidden")
			}
	)
	public ResponseEntity<?> updateStudentById(@RequestParam("id") Long id, @RequestBody StudentDTO studentDTO) {
		try {
			return new ResponseEntity<>(this.studentService.updateStudent(id, studentDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Student Not Found", HttpStatus.NOT_FOUND);
		}
	}
}
