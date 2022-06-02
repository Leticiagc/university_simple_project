package com.ufcg.university.settings;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ufcg.university.mapper.ProfessorMapper;
import com.ufcg.university.mapper.StudentMapper;

@Configuration
public class UniversityMapperConfig {
	
	@Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public StudentMapper studentMapper() {
        return new StudentMapper();
    }

    @Bean
    public ProfessorMapper professorMapper() {
        return new ProfessorMapper();
    }
}
