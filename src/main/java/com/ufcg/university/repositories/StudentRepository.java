package com.ufcg.university.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ufcg.university.entities.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

	Student findByCpf(String cpf);

}
