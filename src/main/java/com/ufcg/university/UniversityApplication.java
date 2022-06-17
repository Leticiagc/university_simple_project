package com.ufcg.university;

import java.io.IOException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ufcg.university.parser.MetricsParser;

@SpringBootApplication
public class UniversityApplication {

	public static void main(String[] args) throws IOException {
		SpringApplication.run(UniversityApplication.class, args);
		MetricsParser mp = new MetricsParser();
	}
}
