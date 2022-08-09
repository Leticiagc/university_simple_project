package com.ufcg.university.services;

import com.ufcg.university.dto.ProfessorDTO;
import com.ufcg.university.entities.Professor;
import com.ufcg.university.mapper.ProfessorMapper;
import com.ufcg.university.repositories.ProfessorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProfessorServiceTest {
    @Autowired
    private ProfessorService profService;

    @Autowired
    private ProfessorMapper professorMapper;

    @Autowired
    private ProfessorRepository profRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ProfessorDTO prof;

    private Professor exemplo;
    private Professor exemplo2;
    @BeforeEach
    public void beforeTests(){
        exemplo = profService.createProfessor(new ProfessorDTO("Ennyo","1234",1,"Programaçao"));
        exemplo2 = profService.createProfessor(new ProfessorDTO("Ramon","78910",4,"Banco de Dados"));
    }

    @AfterEach
    public void afterTests() {
        profRepository.deleteAll();
    }

    @Test
    @DisplayName("Teste para criar um professor")
    public void createdProfessorTest(){
        prof = new ProfessorDTO("Maria","12345",3,"EDA");
        Professor prof2 = new Professor("Maria","12345",3,"EDA");
        Professor prof3 = profService.createProfessor(prof);
        assertTrue(bCryptPasswordEncoder.matches(prof2.getPassword(), prof3.getPassword()));
    }

    @Test
    @DisplayName("Teste para criação de um professor existente")
    public void createdProfessorExistingTest(){
        try {
            prof = new ProfessorDTO("Ennyo","1234",1,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(exemplo,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criação de um professor com string do nome vazio")
    public void createdProfessorNameEmpty(){
        try {
            prof = new ProfessorDTO("","1234",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criação de um professor com string do nome nulo")
    public void createdProfessorNameNull(){
        try {
            prof = new ProfessorDTO(null,"1234",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criação de um professor com string do nome composto por inteiros")
    public void createdProfessorNameStrInt(){
        try {
            prof = new ProfessorDTO("12345","1234",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criação de um professor com string do nome composto por caracteres inválidos")
    public void createdProfessorNameCaractereInvalid(){
        try {
            prof = new ProfessorDTO("$=","1234",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criação de um professor com senha string vazia")
    public void createdProfessorPasswordEmpty(){
        try {
            prof = new ProfessorDTO("Maria","",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com senha string nula")
    public void createdProfessorPasswordNull(){
        prof = new ProfessorDTO("Maria",null,0,"Programaçao");
        Exception thrown = assertThrows(Exception.class, () -> {
            profService.createProfessor(prof);
        });
        assertTrue(thrown.getMessage().contains("rawPassword"));
    }

    @Test
    @DisplayName("Teste para criacao de um professor com string da senha composta por caracteres inválidos")
    public void createdProfessorPasswordCaractereInvalid(){
        try {
            prof = new ProfessorDTO("Maria","$=",0,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com string do tempo de serviço composta por entrada nula")
    public void createdProfessorServiceTimeNull(){
        try {
            prof = new ProfessorDTO("Maria","1234",null,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com string do tempo de serviço composta por entrada inválida")
    public void createdProfessorServiceTimeInvalid(){
        try {
            prof = new ProfessorDTO("Maria","1234",-1,"Programaçao");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com disciplina string vazia")
    public void createdProfessorDisciplineEmpty(){
        try {
            prof = new ProfessorDTO("Maria","1234",0,"");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com disciplina string nula")
    public void createdProfessorDisciplineNull(){
        try {
            prof = new ProfessorDTO("Maria","1234",0,null);
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para criacao de um professor com string da disciplina composta por caracteres inválidos")
    public void createdProfessorDisciplineCaractereInvalid(){
        try {
            prof = new ProfessorDTO("Maria","1234",0,"Progamaçao$=");
            Professor prof2 = profService.createProfessor(prof);
            ProfessorDTO prof3 = profService.getProfessorById(prof2.getId());
            Professor prof4 = professorMapper.convertFromProfessorDTO(prof3);
            assertEquals(prof2,prof4);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para retornar um professor")
    public void getProfessorTest(){
        try {
            ProfessorDTO prof = profService.getProfessorById(exemplo.getId());
            Professor prof2 = professorMapper.convertFromProfessorDTO(prof);
            assertEquals(exemplo,prof2);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("Teste para listar todos os professores")
    public void listProfessors(){
        prof = new ProfessorDTO("Maria","12345",3,"EDA");
        profService.createProfessor(prof);

        ProfessorDTO prof2 = new ProfessorDTO("Ana","9302830",6,"Probabilidade");
        profService.createProfessor(prof2);

        assertNotNull(profService.listProfessors());
    }

    @Test
    @DisplayName("Teste para atualizar um professor")
    public void updateProfessor(){
        ProfessorDTO prof3 = new ProfessorDTO("Ramon", "223344", 4, "EDA");
        try {
            ProfessorDTO profUpdated = profService.updateProfessor(exemplo2.getId(), prof3);
            assertEquals(prof3.getPassword(), profUpdated.getPassword());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    @DisplayName("teste para excluir um professor")
    public void deleteStudent() throws Exception {

        profService.deleteProfessor(exemplo2.getId());
        Exception thrown = assertThrows(Exception.class, () -> {
            prof = profService.getProfessorById(exemplo2.getId());
        });
        assertTrue(thrown.getMessage().contains("Professor Not Found"));
    }
}