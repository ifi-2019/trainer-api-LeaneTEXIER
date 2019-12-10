package com.ifi.trainer_api.controller;

import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.service.TrainerService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.*;
import org.springframework.web.bind.annotation.*;

import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.*;

class TrainerControllerTest {
    @Mock
    private TrainerService trainerService;

    @InjectMocks
    private TrainerController trainerController;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void trainerController_shouldBeAnnotated(){
        var controllerAnnotation =
                TrainerController.class.getAnnotation(RestController.class);
        assertNotNull(controllerAnnotation);

        var requestMappingAnnotation =
                TrainerController.class.getAnnotation(RequestMapping.class);
        assertArrayEquals(new String[]{"/"}, requestMappingAnnotation.value());
    }

    @Test
    void getAllTrainers_shouldCallTheService() {
        trainerController.getAllTrainers("");

        verify(trainerService).getAllTrainers("");
    }

    @Test
    void getAllTrainers_shouldBeAnnotated() throws NoSuchMethodException {
        var getAllTrainers =
                TrainerController.class.getDeclaredMethod("getAllTrainers", String.class);
        var getMapping = getAllTrainers.getAnnotation(GetMapping.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"trainers/{name}"}, getMapping.value());
    }

    @Test
    void getTrainer_shouldCallTheService() {
        trainerController.getTrainer("Ash");

        verify(trainerService).getTrainer("Ash");
    }

    @Test
    void getTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var getTrainer =
                TrainerController.class.getDeclaredMethod("getTrainer", String.class);
        var getMapping = getTrainer.getAnnotation(GetMapping.class);

        var pathVariableAnnotation = getTrainer.getParameters()[0].getAnnotation(PathVariable.class);

        assertNotNull(getMapping);
        assertArrayEquals(new String[]{"trainer/{name}"}, getMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

    @Test
    void createTrainer_shouldCallTheService() {
        Trainer trainer = new Trainer();
        trainerController.createTrainer(trainer);

        verify(trainerService).createTrainer(trainer);
    }

    @Test
    void createTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var createTrainer =
                TrainerController.class.getDeclaredMethod("createTrainer", Trainer.class);
        var postMapping = createTrainer.getAnnotation(PostMapping.class);

        var pathVariableAnnotation = createTrainer.getParameters()[0].getAnnotation(RequestBody.class);

        assertNotNull(postMapping);
        assertArrayEquals(new String[]{"trainer/"}, postMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

    @Test
    void updateTrainer_shouldCallTheService() {
        Trainer trainer = new Trainer();
        trainerController.updateTrainer(trainer);

        verify(trainerService).createTrainer(trainer);
    }

    @Test
    void updateTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var updateTrainer =
                TrainerController.class.getDeclaredMethod("updateTrainer", Trainer.class);
        var putMapping = updateTrainer.getAnnotation(PutMapping.class);

        var pathVariableAnnotation = updateTrainer.getParameters()[0].getAnnotation(RequestBody.class);

        assertNotNull(putMapping);
        assertArrayEquals(new String[]{"trainer/"}, putMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

    @Test
    void deleteTrainer_shouldCallTheService() {
        trainerController.deleteTrainer("Ash");

        verify(trainerService).deleteTrainer("Ash");
    }

    @Test
    void deleteTrainer_shouldBeAnnotated() throws NoSuchMethodException {
        var deleteTrainer =
                TrainerController.class.getDeclaredMethod("deleteTrainer", String.class);
        var deleteMapping = deleteTrainer.getAnnotation(DeleteMapping.class);

        var pathVariableAnnotation = deleteTrainer.getParameters()[0].getAnnotation(PathVariable.class);

        assertNotNull(deleteMapping);
        assertArrayEquals(new String[]{"trainer/{name}"}, deleteMapping.value());

        assertNotNull(pathVariableAnnotation);
    }

}
