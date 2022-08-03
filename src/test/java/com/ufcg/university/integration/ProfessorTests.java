package com.ufcg.university.integration;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.entities.User;
import com.ufcg.university.repositories.ProfessorRepository;
import com.ufcg.university.services.ProfessorService;
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
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureRequest(header = {"Authorization","Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJNYXRoaWFzIiwiaWF0IjoxNjU5NDUwMTUyLCJleHAiOjE2NjAzMTQxNTJ9.0Lp3YpCVuf00t15vEmxxtE9-lFRPJrLLg2wnKNlUH2c"})
public class ProfessorTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private ProfessorRepository professorRepository;

    private final RequestUtils requestUtils = new RequestUtils(this);

    @BeforeEach
    public void beforeTests() {
        professorService.createProfessor(new ProfessorDTO("Mathias","12345678",5,"Computação"));
        professorService.createProfessor(new ProfessorDTO("Ramon","nobrega123",10,"ADS"));
        professorService.createProfessor(new ProfessorDTO("Ennyo José", "123ennyo",5,"Engenharia de Software"));
        //professorService.createProfessor(new ProfessorDTO("Gabriel","boy12345",4,"Informática"));
    }

    @AfterEach
    public void afterTests() {
        professorRepository.deleteAll();
    }

    public static Stream<Arguments> professorCases() {
        return Stream.of(
                Arguments.of(new ProfessorDTO("Mathias","12345678",5,"Computação")),
                Arguments.of(new ProfessorDTO("Ramon","nobrega123",10,"ADS")),
                Arguments.of(new ProfessorDTO("Ennyo José", "123ennyo",5,"Engenharia de Software")),
                Arguments.of(new ProfessorDTO("Gabriel","boy12345",4,"Informática"))
        );
    }

    @Authenticate
    ResultActions login() throws Exception {
        return mockMvc.perform(new Request(requestUtils)
                    .operation(Operation.POST)
                    .endpoint("/login")
                    .body(new User("Mathias","12345678")).execute());
    }

    @Test
    @DisplayName("test get teacher by id")
    @AuthenticatedTest
    public void enpointWhenGettingTeacherById() throws Exception {

        Professor professor = professorService.createProfessor(new ProfessorDTO("Gabriel","boy12345",4,"Informática"));

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.GET)
                .endpoint("/professor/{id}").pathParams(professor.getId())
                .execute())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(professor.getName())))
            .andExpect(jsonPath("$.serviceTime", is(professor.getServiceTime())))
            .andExpect(jsonPath("$.discipline", is(professor.getDiscipline())));
    }

    @Test
    @DisplayName("test delete teacher by id")
    @AuthenticatedTest
    public void endpointWhenDeletingTeacherById() throws Exception {

        Professor professor = professorService.createProfessor(new ProfessorDTO("Gabriel","boy12345",4,"Informática"));

        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("id", professor.getId().toString());

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.DELETE)
                .endpoint("/professor/")
                .params(requestParams).execute())
            .andExpect(status().is2xxSuccessful())
            .andExpect(jsonPath("$.name", is(professor.getName())))
            .andExpect(jsonPath("$.serviceTime", is(professor.getServiceTime())))
            .andExpect(jsonPath("$.discipline", is(professor.getDiscipline())));
    }
}
