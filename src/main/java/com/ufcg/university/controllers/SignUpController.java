package com.ufcg.university.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.ufcg.university.utils.AnnotationToHateoasUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.callbacks.Callback;
import io.swagger.v3.oas.annotations.extensions.Extension;
import io.swagger.v3.oas.annotations.extensions.ExtensionProperty;
import io.swagger.v3.oas.annotations.extensions.Extensions;
import io.swagger.v3.oas.models.OpenAPI;
import org.springdoc.api.AbstractOpenApiResource;
import org.springdoc.webmvc.api.OpenApiResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotationUtils;
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
import java.util.List;

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
				name = "getProfessorById",
				description = "Return the professor by its id.",
				parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
				operationRef = "/professor/{id}/GET"
			),
			@io.swagger.v3.oas.annotations.links.Link(
				name = "deleteProfessorById",
				description = "Delete the professor by its id.",
        parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name")},
				operationRef = "/professor/{id}/DELETE"
			),
			@io.swagger.v3.oas.annotations.links.Link(
				name = "updateProfessorById",
				description = "Update the professor by its id.",
        parameters = {@LinkParameter(name = "id", expression = "$request.path.param_name"),
					@LinkParameter(name = "professorDTO", expression = "#/components/schemas/ProfessorDTO")},
				operationRef = "/professor/{id}/PUT"
			),
			@io.swagger.v3.oas.annotations.links.Link(
				name = "loginProfessor",
				description = "Professor login by name and password",
				operationRef = "http://localhost:8080/login/post"
			)
		}
	)})

	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		Professor professor = this.professorService.createProfessor(professorDTO);
		List<Link> links;
		links = AnnotationToHateoasUtil.getLinksFromMethodClass(SignUpController.class, "createProfessor");
		professor.add(links);

		return new ResponseEntity<>(professor, HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/student", method = RequestMethod.POST)
	@ApiResponse(description = "Create a Student")
	public ResponseEntity<Student> createStudent(@RequestBody StudentDTO studentDTO) {
		return new ResponseEntity<>(this.studentService.createStudent(studentDTO), HttpStatus.CREATED);
	}
}
