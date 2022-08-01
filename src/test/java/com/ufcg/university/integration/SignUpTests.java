package com.ufcg.university.integration;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.Student;
import org.api.mocktests.annotations.AutoConfigureContextType;
import org.api.mocktests.models.Operation;
import org.api.mocktests.models.Request;
import org.api.mocktests.utils.RequestUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureContextType
public class SignUpTests {

    @Autowired
    private MockMvc mockMvc;

    private final RequestUtils requestUtils = new RequestUtils(this);

    public static Stream<Arguments> professorCases() {
        return Stream.of(
                Arguments.of(new ProfessorDTO("Mathias","12345678",5,"Computação")),
                Arguments.of(new ProfessorDTO("Ramon","nobrega123",10,"ADS")),
                Arguments.of(new ProfessorDTO("Ennyo José", "123ennyo",5,"Engenharia de Software")),
                Arguments.of(new ProfessorDTO("Gabriel","boy12345",4,"Informática"))
        );
    }

    public static Stream<Arguments> studentsCases() {
        return Stream.of(
                Arguments.of(new StudentDTO("Leticia","calixto123","1181678")),
                Arguments.of(new StudentDTO("Maely","1234maely","1911765")),
                Arguments.of(new StudentDTO("Vitor", "manel123","1911777"))
        );
    }

    @ParameterizedTest
    @MethodSource("professorCases")
    @DisplayName("test signup teachers")
    public void endpointWhenSavingProfessor(ProfessorDTO professorDTO) throws Exception {
        mockMvc.perform(new Request(requestUtils)
                    .operation(Operation.POST)
                    .endpoint("/signup/professor")
                    .body(professorDTO)
                    .compileRequest())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(professorDTO.getName())))
                .andExpect(jsonPath("$.serviceTime", is(professorDTO.getServiceTime())))
                .andExpect(jsonPath("$.discipline", is(professorDTO.getDiscipline())));
    }

    @ParameterizedTest
    @MethodSource("studentsCases")
    @DisplayName("test signup students")
    public void endpointWhenSavingStudent(StudentDTO studentDTO) throws Exception {
        mockMvc.perform(new Request(requestUtils)
                    .operation(Operation.POST)
                    .endpoint("/signup/student")
                    .body(studentDTO)
                    .compileRequest())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(studentDTO.getName())))
                .andExpect(jsonPath("$.registration", is(studentDTO.getRegistration())));
    }
}

