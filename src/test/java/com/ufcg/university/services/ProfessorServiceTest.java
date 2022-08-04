package com.ufcg.university.services;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.mapper.ProfessorMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

//@SpringBootConfiguration
//@SpringBootTest
//@EnableAutoConfiguration
public class ProfessorServiceTest {
    @Autowired
    private ProfessorService profService;

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ProfessorDTO prof;

    @BeforeEach
    public void beforeTests(){
        profService.createProfessor(new ProfessorDTO("Ennyo","1234",1,"ProgramaÃ§ao"));
        profService.createProfessor(new ProfessorDTO("Ramon","78910",4,"Banco de Dados"));
    }
    @Test
    public void createdProfessorTest(){
        prof = new ProfessorDTO("Maria","12345",3,"EDA");
        Professor prof2 = new Professor("Maria","12345",3,"EDA");
        Professor prof3 = profService.createProfessor(prof);
        assertTrue(bCryptPasswordEncoder.matches(prof2.getPassword(), prof3.getPassword()));
    }

    @Test
    public void getProfessorTest(){
        prof = new ProfessorDTO("Maria","12345",3,"EDA");
        Professor prof2 = profService.createProfessor(prof);
        try {
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void listProfessors(){
        System.out.println("************************************************************");
        profService.listProfessors();
        System.out.println("************************************************************");
    }

    @Test
    public void updateProfessor(){
        prof = new ProfessorDTO("Maria","12345",3,"EDA");
        Professor prof2 = profService.createProfessor(prof);
        ProfessorDTO prof3 = new ProfessorDTO("Maria", "223344", 3, "EDA");
        try {
            ProfessorDTO profUpdated = profService.updateProfessor(prof2.getId(), prof3);
            assertTrue(bCryptPasswordEncoder.matches(prof3.getPassword(), profUpdated.getPassword()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
