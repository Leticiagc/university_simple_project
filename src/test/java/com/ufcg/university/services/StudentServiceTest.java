package com.ufcg.university.services;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import com.ufcg.university.mapper.StudentMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class StudentServiceTest {

    @Autowired
    private StudentService studentService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private StudentDTO studentDTO;

    @Autowired
    private StudentMapper studentMapper;

    @Test
    @DisplayName("teste de criação do estudante")
    public void createdStudentTest() {
        studentDTO = new StudentDTO("Maria", "12345", "119210555");
        Student student = studentService.createStudent(studentDTO);;

        assertEquals(studentDTO.getRegistration(), student.getRegistration());
    }

    @Test
    @DisplayName("teste para retornar um estudante")
    public void getStudentTest() {
        studentDTO = new StudentDTO("Pedro","123456","118210414");
        Student student = studentService.createStudent(studentDTO);

        try {
            StudentDTO studentDTO2 = studentService.getStudentById(student.getId());
            Student student2 = studentService.createStudent(studentDTO2);

            assertTrue(bCryptPasswordEncoder.matches(student.getPassword(), student2.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    @DisplayName("teste para atualizar um estudante")
    public void updateStudent(){
        studentDTO = new StudentDTO("João","1234567","117210455");
        Student student = studentService.createStudent(studentDTO);

        try {
            StudentDTO studentDTO2 = new StudentDTO("João", "123456", "117210455");
            StudentDTO studentUpdateDTO = studentService.updateStudent(student.getId(), studentDTO2);
            assertEquals(studentUpdateDTO.getPassword(), "123456");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("teste para listar todos os estudantes")
    public void listStudents() {
        studentDTO = new StudentDTO("João","1234567","117210455");
        Student student = studentService.createStudent(studentDTO);
        StudentDTO studentDTO2 = new StudentDTO("Pedro","121214","117210258");
        Student student2 = studentService.createStudent(studentDTO2);

        assertNotNull(studentService.listStudents());
    }

    @Test
    @DisplayName("teste para excluir um estudante")
    public void deleteStudent() throws Exception {

        studentDTO = new StudentDTO("Lucas","12345","117210147");
        Student student = studentService.createStudent(studentDTO);

        studentService.deleteStudent(student.getId());

        Exception thrown = assertThrows(Exception.class, () -> {
            StudentDTO studentDTO2 = studentService.getStudentById(student.getId());
        });

        assertTrue(thrown.getMessage().contains("Student Not Found"));

    }
}
