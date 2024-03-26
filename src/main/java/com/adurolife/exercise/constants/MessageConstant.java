package com.adurolife.exercise.constants;

import lombok.Getter;

@Getter
public enum MessageConstant {
  INVALID_INPUT_MESSAGE("Invalid input. Please enter a number between 1 and 10. \n"),
  WELCOME_GAME_MESSAGE(" ========= WELCOME TO BOWLING GAME ========= \n"),
  QUIT_RESET_MESSAGE("Enter 'quit' to exit the game or 'reset' to start a new game."),
  PINS_KNOCKED_DOWN_MESSAGE("Enter the number of pins knocked down (1-10):"),
  CURRENT_FRAME_MESSAGE("Current Frame: %d%n"),
  CURRENT_ROLL_MESSAGE("Current Roll: %d%n"),
  TOTAL_SCORE_MESSAGE("Total Score: %d%n"),
  GAME_RESET_MESSAGE("Game reset. Let's start a new game! \n"),
  FINAL_SCORE_MESSAGE("Final Score: %d%n"),
  GRATEFUL_MESSAGE("Thank you for playing Bowling Game!"),
  ENDED_GAME_MESSAGE("========== ENDED GAME ==================");
  private final String message;
  MessageConstant(String message) {
    this.message = message;
  }

}
