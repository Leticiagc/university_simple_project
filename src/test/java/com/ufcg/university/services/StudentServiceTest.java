package com.ufcg.university.services;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.Student;
import com.ufcg.university.mapper.ProfessorMapper;
import com.ufcg.university.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class StudentServiceTest {

    @Autowired
    private StudentService studentService = new StudentService();
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private StudentDTO studentDTO;

    @Autowired
    private StudentMapper studentMapper;
    
    @Test
    public void createdStudentTest() {
        studentDTO = new StudentDTO("Maria", "12345", "oi");
        Student student2 = new Student("Maria", "12345", "oi");

        Student student = studentService.createStudent(studentDTO);
        assertTrue(bCryptPasswordEncoder.matches(student2.getPassword(), student.getPassword()));
    }

    @Test
    public void getStudentTest(){
        studentDTO = new StudentDTO("Maria","12345","EDA");
        Student student = studentService.createStudent(studentDTO);
        try {
            StudentDTO student2 = studentService.getStudentById(student.getId());
            Student student3 = studentMapper.convertFromStudentDTO(student2);
            assertEquals(student,student3);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void updateStudent(){
        studentDTO = new StudentDTO("Maria","12345","EDA");
        Student student = studentService.createStudent(studentDTO);
        StudentDTO studentDTO2 = new StudentDTO("Maria", "223344", "EDA");
        try {
            StudentDTO studentUpdated = studentService.updateStudent(student.getId(), studentDTO2);
            assertTrue(bCryptPasswordEncoder.matches(studentDTO2.getPassword(), studentUpdated.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
