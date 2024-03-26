package com.adurolife.exercise;

import com.adurolife.exercise.services.BowlingStartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BowlingScoresApplication implements CommandLineRunner {
	private final BowlingStartService bowlingStartService;

	@Autowired
	public BowlingScoresApplication(BowlingStartService bowlingStartService) {
		this.bowlingStartService = bowlingStartService;
	}

	public static void main(String[] args) {
		SpringApplication.run(BowlingScoresApplication.class, args);
	}

	@Override
	public void run(String... args) {
    bowlingStartService.startGame();
	}
}
