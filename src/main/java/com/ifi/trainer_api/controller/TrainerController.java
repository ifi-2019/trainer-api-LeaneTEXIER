package com.ifi.trainer_api.controller;

import com.ifi.trainer_api.bo.Trainer;
import com.ifi.trainer_api.service.TrainerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainers")
public class TrainerController {

    private final TrainerService trainerService;

    TrainerController(TrainerService trainerService){
        this.trainerService = trainerService;
    }

    @GetMapping("/")
    Iterable<Trainer> getAllTrainers(){
        return this.trainerService.getAllTrainers();
    }

    @GetMapping(value = "/{name}")
    Trainer getTrainer(@PathVariable String name){
        return this.trainerService.getTrainer(name);
    }

    @PostMapping("/")
    Trainer createTrainer(@RequestBody Trainer trainer){
        return this.trainerService.createTrainer(trainer);
    }

    @PutMapping("/")
    Trainer updateTrainer(@RequestBody Trainer trainer){
        return this.trainerService.createTrainer(trainer);
    }

    @DeleteMapping("/{name}")
    void deleteTrainer(@PathVariable String name){
        this.trainerService.deleteTrainer(name);
    }

}
