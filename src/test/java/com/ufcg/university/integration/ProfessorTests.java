package com.ufcg.university.integration;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.User;
import com.ufcg.university.repositories.ProfessorRepository;
import com.ufcg.university.services.ProfessorService;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureRequest(mediatype = "application/json", header = {"Authorization","Bearer eyJhbGciOiJIUzI1NiJ9..."})
public class ProfessorTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private MockTest mockTest;

    private static final long INVALID_INPUT = 12458963L;

    @BeforeEach
    public void beforeTests() {
        professorService.createProfessor(new ProfessorDTO("Mathias","12345678",5,"Computação"));
        professorService.createProfessor(new ProfessorDTO("Leticia","calixto123",5,"Computação"));
        professorService.createProfessor(new ProfessorDTO("Maely","1234maely",5,"Computação"));
        professorService.createProfessor(new ProfessorDTO("Vitor", "manel123",5,"Computação"));
    }

    @AfterEach
    public void afterTests() {
        professorRepository.deleteAll();
    }

    public static Stream<Arguments> professorCases() {
        return Stream.of(
                Arguments.of(new ProfessorDTO("Ramon","nobrega123",10,"ADS")),
                Arguments.of(new ProfessorDTO("Ennyo José", "123ennyo",5,"Engenharia de Software")),
                Arguments.of(new ProfessorDTO("Gabriel","boy12345",4,"Informática"))
        );
    }

    public static Stream<Arguments> invalidProfessorCases() {
        return Stream.of(
                Arguments.of(new ProfessorDTO("","nobrega123",10,"ADS")),
                Arguments.of(new ProfessorDTO("Ennyo José", "",5,"Engenharia de Software")),
                Arguments.of(new ProfessorDTO("João","12345oi",4,"")),
                Arguments.of(new ProfessorDTO(null,"boy12345",4,"Informática")),
                Arguments.of(new ProfessorDTO("Daniel","12345senha",null,"Engenharia de Software")),
                Arguments.of(new ProfessorDTO("Jonatas","12345",4,null))
        );
    }

    @Authenticate
    private Request requestLogin = new Request()
            .method(Method.POST)
            .url("/login")
            .body(new User("Mathias", "12345678"));

    @Test
    @DisplayName("test get all teachers")
    @AuthenticatedTest
    public void endpointWhenGettingAllTeachers() throws Exception {

        mockTest.performRequest(new Request().method(Method.GET).url("/professor/"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.*", hasSize(4)));
    }

    @ParameterizedTest
    @DisplayName("test get teacher by id")
    @MethodSource("professorCases")
    @AuthenticatedTest
    public void endpointWhenGettingTeacherById(ProfessorDTO professorDTO) throws Exception {

        Professor professor = professorService.createProfessor(professorDTO);

        mockTest.performRequest(new Request().method(Method.GET).url("/professor/{id}").pathParams(professor.getId()))

                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", is(professor.getName())))
                .andExpect(jsonPath("$.serviceTime", is(professor.getServiceTime())))
                .andExpect(jsonPath("$.discipline", is(professor.getDiscipline())));
    }

    @ParameterizedTest
    @DisplayName("test get nonexistent teacher by id")
    @MethodSource("professorCases")
    @AuthenticatedTest
    public void endpointWhenGettingNonexistentTeacherById() throws Exception {
        mockTest.performRequest(new Request().method(Method.GET).url("/professor/{id}").pathParams(INVALID_INPUT))
                .andExpect(status().isNotFound());

    }

    @ParameterizedTest
    @DisplayName("test delete nonexistent teacher by id")
    @MethodSource("professorCases")
    @AuthenticatedTest
    public void endpointWhenDeletingNonexistentTeacherById() throws Exception {
        mockTest.performRequest(new Request().method(Method.DELETE).url("/professor/").pathParams(INVALID_INPUT))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @DisplayName("test delete teacher by id")
    @MethodSource("professorCases")
    @AuthenticatedTest
    public void endpointWhenDeletingTeacherById(ProfessorDTO professorDTO) throws Exception {

        Professor professor = professorService.createProfessor(professorDTO);

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", professor.getId().toString());

        mockTest.performRequest(new Request().method(Method.DELETE).url("/professor/").params(requestParams))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", is(professor.getName())))
                .andExpect(jsonPath("$.serviceTime", is(professor.getServiceTime())))
                .andExpect(jsonPath("$.discipline", is(professor.getDiscipline())));
    }

    @ParameterizedTest
    @DisplayName("test put teacher by id")
    @MethodSource("professorCases")
    @AuthenticatedTest
    public void endpointWhenUpdatingTeacherById(ProfessorDTO professorDTO) throws Exception {

        Professor professor = professorService.createProfessor(professorDTO);
        ProfessorDTO professorDTO1 = new ProfessorDTO("Gab","12345678",4,"ADS");

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", professor.getId().toString());

        mockTest.performRequest(new Request()
                        .method(Method.PUT)
                        .url("/professor/")
                        .params(requestParams)
                        .body(professorDTO1))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.name", is(professorDTO1.getName())))
                .andExpect(jsonPath("$.serviceTime", is(professorDTO1.getServiceTime())))
                .andExpect(jsonPath("$.discipline", is(professorDTO1.getDiscipline())));
    }

    @ParameterizedTest
    @DisplayName("test put invalid teacher by id")
    @MethodSource("invalidProfessorCases")
    @AuthenticatedTest
    public void endpointWhenUpdatingInvalidTeacherById(ProfessorDTO professorDTO) throws Exception {

        Professor professor = professorService.createProfessor(professorDTO);

        mockTest.performRequest(new Request()
                        .method(Method.PUT)
                        .url("/professor/")
                        .pathParams(professor.getId())
                        .body(professorDTO))
                .andExpect(status().isBadRequest());
    }
}