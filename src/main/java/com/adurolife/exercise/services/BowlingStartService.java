package com.adurolife.exercise.services;

import static com.adurolife.exercise.constants.MessageConstant.CURRENT_FRAME_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.CURRENT_ROLL_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.ENDED_GAME_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.FINAL_SCORE_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.GAME_RESET_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.GRATEFUL_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.INVALID_INPUT_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.PINS_KNOCKED_DOWN_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.QUIT_RESET_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.TOTAL_SCORE_MESSAGE;
import static com.adurolife.exercise.constants.MessageConstant.WELCOME_GAME_MESSAGE;

import com.adurolife.exercise.exceptions.BowlingGameException;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BowlingStartService {

  private static final String QUIT_GAME = "quit";
  private static final String RESET_GAME = "reset";
  private final BowlingGameService bowlingGameService;
  private final Scanner scanner;
  @Autowired
  public BowlingStartService(BowlingGameService bowlingGameService) {
    this.bowlingGameService = bowlingGameService;
    this.scanner = new Scanner(System.in);
  }
  public void startGame() {
    System.out.println(WELCOME_GAME_MESSAGE.getMessage());
    System.out.println(QUIT_RESET_MESSAGE.getMessage());

    while (true) {
      System.out.println(PINS_KNOCKED_DOWN_MESSAGE.getMessage());
      String input = scanner.nextLine().trim();
      switch (input) {
        case QUIT_GAME -> {
          endGame();
          return;
        }
        case RESET_GAME -> resetGame();
        default -> processRollInput(input);
      }
    }
  }
  private void processRollInput(String input) {
    try {
      int pins = Integer.parseInt(input);
      if (pins < 0 || pins > 10) {
        throw new BowlingGameException(INVALID_INPUT_MESSAGE.getMessage());
      }

      if (pins == 0) {
        return;
      }
      bowlingGameService.roll(pins);
      displayGameState();
    } catch (NumberFormatException e) {
      System.out.println(INVALID_INPUT_MESSAGE.getMessage());
    } catch (BowlingGameException e) {
      System.out.println(e.getMessage());
    }
  }
  private void displayGameState() {
    System.out.printf(CURRENT_FRAME_MESSAGE.getMessage(), bowlingGameService.getCurrentFrame());
    System.out.printf(CURRENT_ROLL_MESSAGE.getMessage(), bowlingGameService.getCurrentRoll());
    System.out.printf(TOTAL_SCORE_MESSAGE.getMessage(), bowlingGameService.getScore());
  }

  private void resetGame() {
    bowlingGameService.reset();
    System.out.println(GAME_RESET_MESSAGE.getMessage());
  }

  private void endGame() {
    System.out.printf(FINAL_SCORE_MESSAGE.getMessage(), bowlingGameService.getScore());
    System.out.println(GRATEFUL_MESSAGE.getMessage());
    System.out.println(ENDED_GAME_MESSAGE.getMessage());
    scanner.close();
  }
}
