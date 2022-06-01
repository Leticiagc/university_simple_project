package com.ufcg.university.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.services.ProfessorService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/professor")

public class ProfessorController {
	
	@Autowired
	private ProfessorService professorService;
	
	@GetMapping
	@ApiOperation(value = "List All Professors")
	public ResponseEntity<List<ProfessorDTO>> getAllProfessors() {
		return new ResponseEntity<>(this.professorService.listProfessors(), HttpStatus.OK);
	}
}
