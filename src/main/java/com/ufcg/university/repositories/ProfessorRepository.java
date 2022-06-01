package com.ufcg.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.university.entities.Professor;

@Repository
public interface ProfessorRepository extends JpaRepository<Professor, Long> {}
