package com.ufcg.university.services;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StudentServiceTest {

    StudentService studentService = new StudentService();
    private StudentDTO studentDTO;

    public static Stream<Arguments> studentsCases() {
        return Stream.of (
                Arguments.of(new StudentDTO("Pedro", "123456", "Programação")),
                Arguments.of(new StudentDTO("Matheus", "09876", "Banco de Dados")));
    }
    @Test
    public void createdStudentTest() {
        studentDTO = new StudentDTO("Maria", "12345", "oi");
        Student student2 = new Student("Maria", "12345", "oi");

        assertEquals(student2, studentService.createStudent(studentDTO));
    }
}
