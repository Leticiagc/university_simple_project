package com.ufcg.university.services;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.Student;
import com.ufcg.university.repositories.ProfessorRepository;
import com.ufcg.university.repositories.StudentRepository;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private StudentRepository studentRepository;
    
    @Autowired
    private ProfessorRepository professorRepository;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        Student student = studentRepository.findByCpf(cpf);
        if (student != null) {
        	return new org.springframework.security.core.userdetails.User(student.getCpf(), student.getPassword(), Collections.emptyList());
        }
        Professor professor = professorRepository.findByCpf(cpf);
        if (professor != null) {
        	return new org.springframework.security.core.userdetails.User(professor.getCpf(), professor.getPassword(), Collections.emptyList());
        }
        throw new UsernameNotFoundException(cpf);
    }
    
}
