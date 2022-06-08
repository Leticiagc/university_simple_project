package com.ufcg.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.services.ProfessorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Extension;
import io.swagger.annotations.ExtensionProperty;
import io.swagger.annotations.ResponseHeader;

@RestController
@RequestMapping(value = "/professor")
@Api(value = "Professor", protocols = "HTTP/HTTPS")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "List All Professors",
				  notes = "Multiple Professors can be provided in a list",
				  response = ProfessorDTO.class,
				  responseContainer = "List",
				  httpMethod = "GET",
				  extensions = @Extension(
						  name = "Documentation",
						  properties = {
								  @ExtensionProperty(name = "link", value = "https://openweathermap.org/api")
						  }
				  )
	)
	@ApiModelProperty(allowableValues = "list of professors")
	public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
		return new ResponseEntity<>(this.professorService.listProfessors(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Professor By Id")
	@ApiResponses(
			value = {
					@ApiResponse(code = 200, message = "It's OK", response = ProfessorDTO.class,
							responseHeaders = {
									@ResponseHeader(description = "It's a header")
							}
					),
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
	    @ApiImplicitParam(name = "name", value = "Professor's name", required = true, dataType = "string"),
	    @ApiImplicitParam(name = "password", value = "Professor's password", required = true, dataType = "string"),
	    @ApiImplicitParam(name = "serviceTime", value = "Professor's service time", required = true, dataType = "integer"),
	    @ApiImplicitParam(name = "discipline", value = "Professor's discipline", required = true, dataType = "string")
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
