package com.ufcg.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.services.ProfessorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/professor")
@Api(value = "Professor")
public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ApiOperation(value = "List All Professors")
	public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
		return new ResponseEntity<>(this.professorService.listProfessors(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ApiOperation(value = "Create a Professor")
	public ResponseEntity<Professor> createProfessor(@RequestBody ProfessorDTO professorDTO) {
		return new ResponseEntity<>(this.professorService.createProfessor(professorDTO), HttpStatus.CREATED);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ApiOperation(value = "Get Professor By Id")
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
	public ResponseEntity<?> updateProfessorById(@RequestParam("id") Long id, @RequestBody ProfessorDTO professorDTO) {
		try {
			return new ResponseEntity<>(this.professorService.updateProfessor(id, professorDTO), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<String>("Professor Not Found", HttpStatus.NO_CONTENT);
		}
	}
	
	
	
}
