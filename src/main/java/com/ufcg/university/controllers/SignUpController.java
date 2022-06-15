package com.ufcg.university.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.Student;
import com.ufcg.university.services.ProfessorService;
import com.ufcg.university.services.StudentService;

import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/signup")
public class SignUpController {
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private StudentService studentService;
	
	@RequestMapping(value = "/professor", method = RequestMethod.POST)
	@io.swagger.v3.oas.annotations.Operation( responses = {
		@ApiResponse(
		description = "Create a Professor",
		links = {
			@io.swagger.v3.oas.annotations.links.Link(
				name = "getProfessorById",
				description = "Return the professor by its id.",
				//operationId = "getProfessorById",
				parameters = {
						@LinkParameter(name = "header", expression = "Authorization"),
						@LinkParameter(name = "id")
				},
				operationRef = "/professor/{id}/GET" // Maybe Correct
			),
			@io.swagger.v3.oas.annotations.links.Link(
				name = "deleteProfessorById",
				description = "Delete the professor by its id.",
				//operationId = "deleteProfessorById"
				operationRef = "/professor/{id}/DELETE" // Maybe Correct
			),
			@io.swagger.v3.oas.annotations.links.Link(
				name = "updateProfessorById",
				description = "Update the professor by its id.",
				//operationId = "updateProfessorById"
				operationRef = "/professor/{id}/PUT" // Maybe Correct
			)
		}
	)})
	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		Professor professor = this.professorService.createProfessor(professorDTO);
		Link[] links = new Link[] {
			linkTo(methodOn(SignUpController.class).createProfessor(professorDTO)).withSelfRel().withType("POST"),
			linkTo(methodOn(ProfessorController.class).getProfessorById(professor.getId())).withRel("getProfessor").withType("GET"),
			linkTo(methodOn(ProfessorController.class).deleteProfessorById(professor.getId())).withRel("deleteProfessor").withType("DELETE"),
			linkTo(methodOn(ProfessorController.class).updateProfessorById(professor.getId(), null)).withRel("putProfessor").withType("PUT")
		};
		professor.add(links);

		return new ResponseEntity<>(professor, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ApiResponse(description = "Create a Student")
	public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<>(this.studentService.createStudent(studentDTO), HttpStatus.CREATED);
	}
}
