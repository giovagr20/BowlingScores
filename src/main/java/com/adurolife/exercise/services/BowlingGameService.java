package com.adurolife.exercise.services;

import com.adurolife.exercise.exceptions.BowlingGameException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class BowlingGameService {
    private final int[] rolls = new int[21];
    @Getter
    private int currentFrame;
    @Getter
    private int currentRoll;
    @Getter
    private int score;
    private int rollIndex;

    public BowlingGameService() {
        this.currentFrame = 1;
        this.currentRoll = 1;
        this.score = 0;
        this.rollIndex = 0;
    }

    public void roll(int pins) {
        recordRoll(pins);

        if (isStrike(pins)) {
            handleStrike();
        } else if (isSpare()) {
            handleSpare();
        } else {
            handleRegularRoll(pins);
        }
        checkCurrentRoll();
        checkTenthFrame(pins);
    }

    public void reset() {
        currentFrame = 1;
        currentRoll = 1;
        score = 0;
        rollIndex = 0;
        Arrays.fill(rolls, 0);
    }

    private void checkTenthFrame(int pins) {
        if (currentFrame == 10) {
            if (isSpare()) {
                makeBonus(pins);
            }

            if (isStrike(pins)) {
                makeBonus(pins);
            }

            if (currentRoll > 3) {
                throw new BowlingGameException("It is the tenth frame and your third roll, you can not roll more.");
            }
        }
    }

    private void makeBonus(int pins) {
        currentRoll++;
        score+=pins;
    }
    private void recordRoll(int pins) {
        rolls[rollIndex++] = pins;
    }

    private void checkCurrentRoll() {
        if (currentRoll == 2) {
            currentRoll = 1;
            currentFrame++;
        } else {
            currentRoll++;
        }
    }
    private boolean isStrike(int pins) {
        return pins == 10;
    }

    private boolean isSpare() {
        return rollIndex >= 2 && rolls[rollIndex - 2] + rolls[rollIndex - 1] == 10;
    }

    private void handleStrike() {
        score += 10 + strikesBonus();
    }

    private void handleSpare() {
        score += 10 + spareBonus();
    }

    private void handleRegularRoll(int pins) {
        score += pins;
    }

    private int strikesBonus() {
        return rolls[rollIndex - 1] + rolls[rollIndex];
    }

    private int spareBonus() {
        return rolls[rollIndex - 1];
    }
}
