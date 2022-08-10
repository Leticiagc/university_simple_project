package com.ufcg.university.integration;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import com.ufcg.university.entities.User;
import com.ufcg.university.repositories.StudentRepository;
import com.ufcg.university.services.StudentService;
import org.api.mocktests.annotations.Authenticate;
import org.api.mocktests.annotations.AuthenticatedTest;
import org.api.mocktests.annotations.AutoConfigureRequest;
import org.api.mocktests.models.Method;
import org.api.mocktests.models.Request;
import org.api.mocktests.utils.MockTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.LinkedMultiValueMap;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@EnableAutoConfiguration
@AutoConfigureRequest
public class StudentTests {

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MockTest mockTest;

    @BeforeEach
    public void beforeTests() {

        studentService.createStudent(new StudentDTO("Mathias","12345678","11811111"));
        studentService.createStudent(new StudentDTO("Ramon","nobrega123","23456789"));
        studentService.createStudent(new StudentDTO("Ennyo José", "123ennyo","23456789"));
        studentService.createStudent(new StudentDTO("Gabriel","boy12345","12345673"));
    }

    public static Stream<Arguments> studentsCases() {
        return Stream.of(
                Arguments.of(new StudentDTO("Leticia","calixto123","1181678")),
                Arguments.of(new StudentDTO("Maely","1234maely","1911765")),
                Arguments.of(new StudentDTO("Vitor", "manel123","1911777"))
        );
    }

    @AfterEach
    public void afterTests() {
        studentRepository.deleteAll();
    }

    @Authenticate
    private Request requestLogin = new Request()
            .method(Method.POST)
            .url("/login")
            .body(new User("Mathias","12345678"));

    @Test
    @DisplayName("test get all students")
    @AuthenticatedTest
    public void endpointWhenGettingAllStudents() throws Exception {

        mockTest.performRequest(new Request()
                .method(Method.GET)
                .url("/student/"))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @ParameterizedTest
    @MethodSource("studentsCases")
    @DisplayName("test get student by id")
    @AuthenticatedTest
    public void endpointWhenGettingStudentById(StudentDTO studentDTO) throws Exception {

        Student student = studentService.createStudent(studentDTO);

        mockTest.performRequest(new Request()
                .method(Method.GET)
                .url("/student/{id}").pathParams(student.getId()))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(student.getName())))
            .andExpect(jsonPath("$.registration", is(student.getRegistration())));
    }

    @ParameterizedTest
    @MethodSource("studentsCases")
    @DisplayName("test delete student by id")
    @AuthenticatedTest
    public void endpointWhenDeletingStudentById(StudentDTO studentDTO) throws Exception {

        Student student = studentService.createStudent(studentDTO);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", student.getId().toString());

        mockTest.performRequest(new Request()
                .method(Method.DELETE)
                .url("/student/")
                .params(requestParams))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(student.getName())))
            .andExpect(jsonPath("$.registration", is(student.getRegistration())));
    }

    @ParameterizedTest
    @MethodSource("studentsCases")
    @DisplayName("test put student by id")
    @AuthenticatedTest
    public void endpointWhenUpdatingStudentById(StudentDTO studentDTO) throws Exception {

        Student student = studentService.createStudent(studentDTO);
        StudentDTO studentDTO1 = new StudentDTO("Gab", "12345678","1234567890");

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", student.getId().toString());

        mockTest.performRequest(new Request()
                .method(Method.PUT)
                .url("/student/")
                .params(requestParams)
                .body(studentDTO1))
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(studentDTO1.getName())))
            .andExpect(jsonPath("$.registration", is(studentDTO1.getRegistration())));
    }
}
