package com.ifi.trainer_api.controller;

import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.service.TrainerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class TrainerController {

    private final TrainerService trainerService;

    TrainerController(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @GetMapping("trainers/{name}")
    Iterable<Trainer> getAllTrainers(@PathVariable String name){
        return this.trainerService.getAllTrainers(name);
    }

    @GetMapping(value = "trainer/{name}")
    Trainer getTrainer(@PathVariable String name){
        return this.trainerService.getTrainer(name);
    }

    @PostMapping("trainer/")
    Trainer createTrainer(@RequestBody Trainer trainer){
        return this.trainerService.createTrainer(trainer);
    }

    @PutMapping("trainer/")
    Trainer updateTrainer(@RequestBody Trainer trainer){
        return this.trainerService.createTrainer(trainer);
    }

    @DeleteMapping("trainer/{name}")
    void deleteTrainer(@PathVariable String name){
        this.trainerService.deleteTrainer(name);
    }

}
