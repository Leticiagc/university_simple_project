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

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.services.ProfessorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping(value = "/professor")
@Api(value = "Professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "List All Professors",
				  notes = "Multiple Professors can be provided in a list",
				  response = ProfessorDTO.class,
				  responseContainer = "List"
	)
	
	@ApiModelProperty(allowableValues = "list of professors")
	public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
		return new ResponseEntity<>(this.professorService.listProfessors(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Create a Professor", 
		authorizations = {
			    @Authorization(
				        value = "universityoauth", // scheme 
					    scopes = {
					        @AuthorizationScope(
					        		scope = "add:professor", 
					        		description = "Allows adding of professors")
						     }
				)
		},
		responseHeaders = {
				@ResponseHeader(description = "It's return a Status Code")
		}
	)
	
	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		return new ResponseEntity<>(this.professorService.createProfessor(professorDTO), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Professor By Id")
	@ApiResponses(
			value = {
					@ApiResponse(code = 400, message = "Invalid ID"),
					@ApiResponse(code = 404, message = "Professor Not Found") 
			}
	)
	public ResponseEntity<?> getProfessorById(@PathVariable("id") Long id) {
		try {
			return new ResponseEntity<>(this.professorService.getProfessorById(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Professor Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.DELETE)
	@ApiOperation(value = "Delete Professor By Id")
	public ResponseEntity<?> deleteProfessorById(@RequestParam("id") Long id) {
		try {
			return new ResponseEntity<>(this.professorService.deleteProfessor(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Professor Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@ApiOperation(value = "Update Professor")
	@ApiImplicitParams({
	    @ApiImplicitParam(name = "name", value = "Professor's name", required = true, dataType = "string", paramType = "body"),
	    @ApiImplicitParam(name = "password", value = "Professor's password", required = true, dataType = "string", paramType = "body"),
	    @ApiImplicitParam(name = "serviceTime", value = "Professor's service time", required = true, dataType = "int", paramType = "body"),
	    @ApiImplicitParam(name = "discipline", value = "Professor's discipline", required = true, dataType = "string", paramType = "body")
	})
	public ResponseEntity<?> updateProfessorById(@RequestParam("id") Long id, 
												 @ApiParam(value = "Updated professor object", required = true) ProfessorDTO professorDTO) {
		try {
			return new ResponseEntity<>(this.professorService.updateProfessor(id, professorDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Professor Not Found", HttpStatus.NO_CONTENT);
		}
	}
}
