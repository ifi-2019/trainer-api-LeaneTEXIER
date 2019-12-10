package com.ifi.trainer_api.controller;

import com.ifi.trainer_api.bo.Pokemon;
import com.ifi.trainer_api.bo.Trainer;

import com.ifi.trainer_api.repository.TrainerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TrainerControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TrainerController controller;

    @Autowired
    private TrainerRepository repository;

    @Value("${spring.security.user.name}")
    private String username;

    @Value("${spring.security.user.password}")
    private String password;


    @BeforeEach
    void setUp() {
        var ash = new Trainer("Ash");
        var pikachu = new Pokemon(25, 18);
        ash.setTeam(List.of(pikachu));

        var misty = new Trainer("Misty");
        var staryu = new Pokemon(120, 18);
        var starmie = new Pokemon(121, 21);
        misty.setTeam(List.of(staryu, starmie));

        // save a couple of trainers
        repository.save(ash);
        repository.save(misty);
    }

    @AfterEach
    void tearDown() {
        repository.deleteAll();
    }

    @Test
    void getTrainers_shouldThrowAnUnauthorized(){
        var responseEntity = this.restTemplate
                .getForEntity("http://localhost:" + port + "/trainers/Ash", Trainer.class);
        assertNotNull(responseEntity);
        assertEquals(401, responseEntity.getStatusCodeValue());
    }

    @Test
    void trainerController_shouldBeInstanciated(){
        assertNotNull(controller);
    }

    @Test
    void getTrainer_withNameAsh_shouldReturnAsh() {
        var ash = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainer/Ash", Trainer.class);
        assertNotNull(ash);
        assertEquals("Ash", ash.getName());
        assertEquals(1, ash.getTeam().size());

        assertEquals(25, ash.getTeam().get(0).getPokemonType());
        assertEquals(18, ash.getTeam().get(0).getLevel());
    }

    @Test
    void getAllTrainers_from_Ash_shouldReturnMistyAndBug() {
        var bug = new Trainer("Bug");
        var pikachu = new Pokemon(25, 5);
        bug.setTeam(List.of(pikachu));
        repository.save(bug);

        var trainers = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainers/Ash", Trainer[].class);
        assertNotNull(trainers);
        assertEquals(2, trainers.length);

        assertEquals("Misty", trainers[0].getName());
        assertEquals("Bug", trainers[1].getName());
    }

    @Test
    void createTrainer_withNameBugCatcherAndTeamOf2Pokemons_shouldReturnTrainerBugCatcherWith2Pokemons() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("{\"name\": \"Bug Catcher\",\"team\": [{\"pokemonType\": 13, \"level\": 6},{\"pokemonType\": 10, \"level\": 6}]}", headers);

        var bugCatcher = this.restTemplate
                .withBasicAuth(username, password)
                .postForObject("http://localhost:" + port + "/trainer/", entity, Trainer.class);
        assertNotNull(bugCatcher);
        assertEquals("Bug Catcher", bugCatcher.getName());
        assertEquals(2, bugCatcher.getTeam().size());

        assertEquals(13, bugCatcher.getTeam().get(0).getPokemonType());
        assertEquals(6, bugCatcher.getTeam().get(0).getLevel());

        assertEquals(10, bugCatcher.getTeam().get(1).getPokemonType());
        assertEquals(6, bugCatcher.getTeam().get(1).getLevel());
    }

    @Test
    void updateTrainer_withNameMistyAndTeamOf3Pokemons_shouldReturnTrainerMistyWith3Pokemons() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>("{\"name\": \"Misty\",\"team\": [{\"pokemonType\": 13, \"level\": 7},{\"pokemonType\": 9, \"level\": 6},{\"pokemonType\": 5, \"level\": 7}]}", headers);

        var misty = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainer/Misty", Trainer.class);
        assertNotNull(misty);
        assertEquals("Misty", misty.getName());
        assertEquals(2, misty.getTeam().size());

        this.restTemplate
                .withBasicAuth(username, password)
                .put("http://localhost:" + port + "/trainer/", entity, Trainer.class);
        misty = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainer/Misty", Trainer.class);
        assertNotNull(misty);
        assertEquals("Misty", misty.getName());
        assertEquals(3, misty.getTeam().size());

        assertEquals(13, misty.getTeam().get(0).getPokemonType());
        assertEquals(7, misty.getTeam().get(0).getLevel());

        assertEquals(9, misty.getTeam().get(1).getPokemonType());
        assertEquals(6, misty.getTeam().get(1).getLevel());

        assertEquals(5, misty.getTeam().get(2).getPokemonType());
        assertEquals(7, misty.getTeam().get(2).getLevel());
    }

    @Test
    void deleteTrainer_withNameAsh() {
        var ash = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainer/Ash", Trainer.class);
        assertNotNull(ash);
        this.restTemplate
                .withBasicAuth(username, password)
                .delete("http://localhost:" + port + "/trainer/Ash", Trainer.class);
        ash = this.restTemplate
                .withBasicAuth(username, password)
                .getForObject("http://localhost:" + port + "/trainer/Ash", Trainer.class);
        assertNull(ash);
    }

}