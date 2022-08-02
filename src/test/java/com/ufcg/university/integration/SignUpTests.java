package com.ufcg.university.integration;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.dto.StudentDTO;
import com.ufcg.university.entities.User;
import com.ufcg.university.repositories.ProfessorRepository;
import com.ufcg.university.repositories.StudentRepository;
import com.ufcg.university.services.ProfessorService;
import com.ufcg.university.services.StudentService;
import org.api.mocktests.annotations.AutoConfigureRequest;
import org.api.mocktests.models.Operation;
import org.api.mocktests.models.Request;
import org.api.mocktests.utils.RequestUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@EnableAutoConfiguration
@AutoConfigureMockMvc
@AutoConfigureRequest
public class SignUpTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProfessorService professorService;

    @Autowired
    private StudentService studentService;

    @Autowired
    private ProfessorRepository professorRepository;

    @Autowired
    private StudentRepository studentRepository;

    private final RequestUtils requestUtils = new RequestUtils(this);

    @AfterEach
    public void afterTests() {
        professorRepository.deleteAll();
        studentRepository.deleteAll();
    }

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

    public static Stream<Arguments> teachersNullablesCases() {
        return Stream.of(
                Arguments.of(new User("Mathias","123456789")),
                Arguments.of(new User("Ramon",null)),
                Arguments.of(new User("José Ennyo", "123ennyo")),
                Arguments.of(new User(null,"boy12345"))
        );
    }

    public static Stream<Arguments> studentsNullablesCases() {
        return Stream.of(
                Arguments.of(new User("Leticia","CALIXTO123")),
                Arguments.of(new User("Maely","")),
                Arguments.of(new User("VITOR","manel123"))
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
                    .execute())
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
                    .body(studentDTO).execute())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(studentDTO.getName())))
                .andExpect(jsonPath("$.registration", is(studentDTO.getRegistration())));
    }

    @ParameterizedTest
    @MethodSource("professorCases")
    @DisplayName("test teachers login")
    public void endpointWhenLoggingTeacher(ProfessorDTO professorDTO) throws Exception {

        professorService.createProfessor(professorDTO);

        Objects.requireNonNull(mockMvc.perform(new Request(requestUtils)
                .operation(Operation.POST)
                .endpoint("/login")
                .body(new User(professorDTO.getName(), professorDTO.getPassword())).execute())
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getHeader("Authorization"));
    }

    @ParameterizedTest
    @MethodSource("studentsCases")
    @DisplayName("test students login")
    public void endpointWhenLoggingStudent(StudentDTO studentDTO) throws Exception {

        studentService.createStudent(studentDTO);

        Objects.requireNonNull(mockMvc.perform(new Request(requestUtils)
                .operation(Operation.POST).endpoint("/login")
                .body(new User(studentDTO.getName(), studentDTO.getPassword())).execute())
            .andExpect(status().is2xxSuccessful())
            .andReturn().getResponse().getHeader("Authorization"));
    }

    @ParameterizedTest
    @MethodSource("teachersNullablesCases")
    @DisplayName("test invalid teachers login")
    public void endpointWhenLogginInvalidTeachers(User user) throws Exception {

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.POST)
                .endpoint("/login")
                .body(user).execute())
            .andExpect(status().isUnauthorized());
    }

    @ParameterizedTest
    @MethodSource("studentsNullablesCases")
    @DisplayName("test invalid students login")
    public void endpointWhenLoggingInvalidStudents(User user) throws Exception {

        mockMvc.perform(new Request(requestUtils)
                .operation(Operation.POST)
                .endpoint("/login")
                .body(user).execute())
            .andExpect(status().isUnauthorized());
    }
}

