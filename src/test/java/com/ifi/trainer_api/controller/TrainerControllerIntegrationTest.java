package com.ifi.trainer_api.controller;

import com.ifi.trainer_api.bo.Trainer;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Test
    void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }

    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonType());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_shouldReturnAshAndMisty() {
        var trainers = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Ash", trainers[0].getName());
        assertEquals("Misty", trainers[1].getName());
    }

//    @Test
//    void createTrainer_withNameBugCatcherAndTeamOf2Pokemons_shouldReturnTrainerBugCatcherWith2Pokemons() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>("{\"name\": \"Bug Catcher\",\"team\": [{\"pokemonType\": 13, \"level\": 6},{\"pokemonType\": 10, \"level\": 6}]}", headers);
//
//        var bugCatcher = this.restTemplate.postForObject("http://localhost:" + port + "/trainers/", entity, Trainer.class);
//        assertNotNull(bugCatcher);
//        assertEquals("Bug Catcher", bugCatcher.getName());
//        assertEquals(2, bugCatcher.getTeam().size());
//
//        assertEquals(13, bugCatcher.getTeam().get(0).getPokemonType());
//        assertEquals(6, bugCatcher.getTeam().get(0).getLevel());
//
//        assertEquals(10, bugCatcher.getTeam().get(1).getPokemonType());
//        assertEquals(6, bugCatcher.getTeam().get(1).getLevel());
//    }

//    @Test
//    void updateTrainer_withNameBugCatcherAndTeamOf2Pokemons_shouldReturnTrainerBugCatcherWith2Pokemons() {
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<String> entity = new HttpEntity<String>("{\"name\": \"Misty\",\"team\": [{\"pokemonType\": 13, \"level\": 7},{\"pokemonType\": 9, \"level\": 6},{\"pokemonType\": 5, \"level\": 7}]}", headers);
//
//        var misty = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Misty", Trainer.class);
//        assertNotNull(misty);
//        assertEquals("Misty", misty.getName());
//        assertEquals(2, misty.getTeam().size());
//
//        this.restTemplate.put("http://localhost:" + port + "/trainers/", entity, Trainer.class);
//        misty = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Misty", Trainer.class);
//        assertNotNull(misty);
//        assertEquals("Misty", misty.getName());
//        assertEquals(3, misty.getTeam().size());
//
//        assertEquals(13, misty.getTeam().get(0).getPokemonType());
//        assertEquals(7, misty.getTeam().get(0).getLevel());
//
//        assertEquals(9, misty.getTeam().get(1).getPokemonType());
//        assertEquals(6, misty.getTeam().get(1).getLevel());
//
//        assertEquals(5, misty.getTeam().get(2).getPokemonType());
//        assertEquals(7, misty.getTeam().get(2).getLevel());
//    }

//    @Test
//    void deleteTrainer_withNameAsh() {
//        var ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
//        assertNotNull(ash);
//        this.restTemplate.delete("http://localhost:" + port + "/trainers/Ash", Trainer.class);
//        ash = this.restTemplate.getForObject("http://localhost:" + port + "/trainers/Ash", Trainer.class);
//        assertNull(ash);
//    }

}