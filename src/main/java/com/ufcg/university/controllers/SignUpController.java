package com.ufcg.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
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
import com.ufcg.university.utils.AnnotationToHateoasUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping(value = "/signup")
@DependsOn({"openApiResource"})
public class SignUpController {
	
	@Autowired
	private ProfessorService professorService;
	
	@Autowired
	private StudentService studentService;

	@Autowired
	private ApplicationContext applicationContext;
	
	@RequestMapping(value = "/professor", method = RequestMethod.POST)
	@Operation(
		summary = "Create a Student",
		description = "Add a student to the system",
		responses = {
			@ApiResponse(
				description = "Create a Professor",
				responseCode = "200",
				links = {
					@io.swagger.v3.oas.annotations.links.Link(
						name = "getProfessor",
						description = "Return the professor by its id.",
						parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
						operationRef = "http://localhost:8080/professor/{id}/get"
					),
					@io.swagger.v3.oas.annotations.links.Link(
						name = "deleteProfessor",
						description = "Delete the professor by its id.",
						parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
						operationRef = "http://localhost:8080/professor/{id}/delete"
					),
					@io.swagger.v3.oas.annotations.links.Link(
						name = "updateProfessor",
						description = "Update the professor by its id.",
						parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name"),
							@LinkParameter(name = "professorDTO", expression = "#/components/schemas/ProfessorDTO")},
						operationRef = "http://localhost:8080/professor/{id}/put"
					),
					@io.swagger.v3.oas.annotations.links.Link(
						name = "loginProfessor",
						description = "Professor login by name and password",
						operationRef = "http://localhost:8080/login/post"
					)
				}
			)
		}
	)
	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		Professor professor = this.professorService.createProfessor(professorDTO);
		List<Link> links;
		links = AnnotationToHateoasUtil.getLinksFromMethodClass(SignUpController.class, "createProfessor");
		professor.add(links);

		return new ResponseEntity<>(professor, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@Operation( 
		summary = "Create a Student",
		description = "Add a student to the system",
		responses = {
			@ApiResponse(
					description = "Create a Student",
					links = {
							@io.swagger.v3.oas.annotations.links.Link(
								name = "getStudent",
								description = "Return the student by its id.",
								parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
								operationRef = "http://localhost:8080/student/{id}/get"
							),
							@io.swagger.v3.oas.annotations.links.Link(
								name = "deleteStudent",
								description = "Delete the student by its id.",
								parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
								operationRef = "http://localhost:8080/student/{id}/delete"
							),
							@io.swagger.v3.oas.annotations.links.Link(
								name = "updateStudent",
								description = "Update the student by its id.",
								parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name"),
									@LinkParameter(name = "studentDTO", expression = "#/components/schemas/StudentDTO")},
								operationRef = "http://localhost:8080/student/{id}/put"
							),
							@io.swagger.v3.oas.annotations.links.Link(
								name = "loginStudent",
								description = "Student login by name and password",
								operationRef = "http://localhost:8080/login/post"
							)
					}
			)
		}
	)
	public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<>(this.studentService.createStudent(studentDTO), HttpStatus.CREATED);
	}
}
