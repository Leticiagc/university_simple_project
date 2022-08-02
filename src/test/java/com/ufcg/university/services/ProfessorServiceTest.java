package com.ufcg.university.services;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

//@SpringBootConfiguration
//@SpringBootTest
//@EnableAutoConfiguration
public class ProfessorServiceTest {
    //    @MockBean
    ProfessorService profService = new ProfessorService();
    private ProfessorDTO prof;

    public static Stream<Arguments> professorCases(){
        return Stream.of(
                Arguments.of(new ProfessorDTO("JoÃ£o","1234",1,"ProgramaÃ§ao")),
                Arguments.of(new ProfessorDTO("Ramon","78910",4,"Banco de Dados"))
        );

    }
    @Test
    public void createdProfessorTest(){
        System.out.println(" sdfas");
        prof = new ProfessorDTO("Maria","12345",3,"BZ");
        Professor prof2 = new Professor("Maria","12345",3,"BZ");
        assertEquals(prof2,profService.createProfessor(prof));
    }
}
