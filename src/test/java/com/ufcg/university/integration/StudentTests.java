package com.ufcg.university.integration;

import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Student;
import com.ufcg.university.entities.User;
import com.ufcg.university.repositories.StudentRepository;
import com.ufcg.university.services.StudentService;
import org.api.mocktests.annotations.Authenticate;
import org.api.mocktests.annotations.AuthenticatedTest;
import org.api.mocktests.annotations.AutoConfigureRequest;
import org.api.mocktests.models.Operation;
import org.api.mocktests.models.Request;
import org.api.mocktests.utils.RequestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureRequest
public class StudentTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private StudentService studentService;

    @Autowired
    private StudentRepository studentRepository;

    private final RequestUtils requestUtils = new RequestUtils(this);

    @BeforeEach
    public void beforeTests() {

        studentService.createStudent(new StudentDTO("Leticia","calixto123","1181678"));
        studentService.createStudent(new StudentDTO("Maely","1234maely","1911765"));
        studentService.createStudent(new StudentDTO("Vitor", "manel123","1911777"));

        studentService.createStudent(new StudentDTO("Mathias","12345678","11811111"));
    }

    @AfterEach
    public void afterTests() {
        studentRepository.deleteAll();
    }

    @Authenticate
    ResultActions login() throws Exception {
        return mockMvc.perform(new Request(requestUtils)
                    .operation(Operation.POST)
                    .endpoint("/login")
                    .body(new User("Mathias","12345678")).execute());
    }

    @Test
    @DisplayName("test get student by id")
    @AuthenticatedTest
    public void endpointWhenGettingStudentById() throws Exception {

        Student student = studentService.createStudent(new StudentDTO("Gabriel","boy12345","11817654"));

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.GET)
                .endpoint("/student/{id}").pathParams(student.getId())
                .execute())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(student.getName())))
            .andExpect(jsonPath("$.registration", is(student.getRegistration())));
    }

    @Test
    @DisplayName("test delete student by id")
    @AuthenticatedTest
    public void endpointWhenDeletingStudentById() throws Exception {

        Student student = studentService.createStudent(new StudentDTO("Gabriel","boy12345","11817654"));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", student.getId().toString());

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.DELETE)
                .endpoint("/student/")
                .params(requestParams).execute())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(student.getName())))
            .andExpect(jsonPath("$.registration", is(student.getRegistration())));
    }
}
