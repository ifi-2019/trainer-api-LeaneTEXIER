package com.ifi.trainer_api.service;

import com.ifi.trainer_api.bo.Trainer;

import java.util.List;

public interface TrainerService {
    List<Trainer> getAllTrainers(String name);
    Trainer getTrainer(String name);
    Trainer createTrainer(Trainer trainer);
    void deleteTrainer(String name);
}
