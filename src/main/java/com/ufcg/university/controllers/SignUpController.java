package com.ufcg.university.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.extensions.Extensions;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.webmvc.api.OpenApiResource;
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

import io.swagger.v3.oas.annotations.links.LinkParameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

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
	@Operation( responses = {
		@ApiResponse(
		description = "Create a Professor",
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
			)
		}
	)})

	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		Professor professor = this.professorService.createProfessor(professorDTO);
		Link[] links;

		String[] beans = applicationContext.getBeanDefinitionNames();
//		OpenApiResource openAPI = (OpenApiResource) applicationContext.getBean("openApiResource");
//		openAPI.openapiJson()
//		System.out.println();
//		try {
//			URL url = new URL("http://localhost:8080/v3/api-docs");
//			HttpURLConnection con = (HttpURLConnection) url.openConnection();
//			InputStream inputStream = con.getInputStream();
//		} catch (MalformedURLException e) {
//			throw new RuntimeException(e);
//		} catch (IOException e) {
//			throw new RuntimeException(e);
//		}

		for(String bean:beans){
			if (bean.equals("customOpenAPI")) {
				System.out.println("Bean name: " + bean);
				Object object = applicationContext.getBean(bean);
				System.out.println( "Bean object:" + object);
			}
		}
//		for ()
//		links = new Link[] {
//			linkTo(methodOn(SignUpController.class).createProfessor(professorDTO)).withSelfRel().withType("POST"),
//			linkTo(methodOn(ProfessorController.class).getProfessorById(professor.getId())).withRel("getProfessor").withType("GET")
//					.withTitle("Return the professor by its id."),
//			linkTo(methodOn(ProfessorController.class).deleteProfessorById(professor.getId())).withRel("deleteProfessor").withType("DELETE")
//					.withTitle("Delete the professor by its id."),
//			linkTo(methodOn(ProfessorController.class).updateProfessorById(professor.getId(), null)).withRel("putProfessor").withType("PUT")
//					.withTitle("Update the professor by its id."),
//			Link.of("localhost:8080/login").withRel("login").withTitle("Professor login")
//		};
//		professor.add(links);

		return new ResponseEntity<>(professor, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ApiResponse(description = "Create a Student")
	public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<>(this.studentService.createStudent(studentDTO), HttpStatus.CREATED);
	}
}
