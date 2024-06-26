package com.adurolife.exercise.services;

import com.adurolife.exercise.exceptions.BowlingGameException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.NumberUtils;

import static org.junit.jupiter.api.Assertions.*;

class BowlingGameServiceTest {
    private BowlingGameService bowlingGameService;

    @BeforeEach
    void setUp() {
        bowlingGameService = new BowlingGameService();
    }

    @Test
    void givenAStrike_whenIsFirstFrame_thenThereIsBonusPlus10() {
        final int PINS = 10;
        final int EXPECTED_RESULT = 20;
        bowlingGameService.roll(PINS);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
        assertEquals(1, bowlingGameService.getCurrentFrame());
    }

    @Test
    void givenAStrikeInSecondTry_whenIsSecondFrame_thenThereIsBonusPlus10AndBeforeResult() {

        final int PINS_FIRST_TRY = 5;
        final int PINS_SECOND_TRY = 10;
        final int EXPECTED_RESULT = 25;
        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
        assertEquals(2, bowlingGameService.getCurrentFrame());
    }

    @Test
    void givenARegularGameWithNoSparesAndStrikes_thenThereIsNoBonusAndJustSumOfPins() {

        final int PINS_FIRST_TRY = 5;
        final int PINS_SECOND_TRY = 2;
        final int EXPECTED_RESULT = 7;
        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
    }

    @Test
    void givenASpare_whenIsSecondFrameAndFirstRoll_thenPlus10() {
        final int PINS_FIRST_TRY = 3;
        final int PINS_SECOND_TRY = 7;
        final int EXPECTED_RESULT = 20;

        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
        assertEquals(2, bowlingGameService.getCurrentFrame());
    }

    @Test
    void givenASpare_whenIsSecondFrameAndSecondRoll_thenPlus10() {
        final int PINS_FIRST_TRY = 1;
        final int PINS_SECOND_TRY = 7;
        final int PINS_THIRD_TRY = 3;
        final int EXPECTED_RESULT = 21;

        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);
        bowlingGameService.roll(PINS_THIRD_TRY);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
        assertEquals(2, bowlingGameService.getCurrentFrame());
        assertEquals(2, bowlingGameService.getCurrentRoll());
    }

    @Test
    void givenASpare_whenIsThirdFrameAndFirstRoll_thenPlus10() {
        final int PINS_FIRST_TRY = 1;
        final int PINS_SECOND_TRY = 7;
        final int PINS_THIRD_TRY = 4;
        final int PINS_FOURTH_TRY = 6;
        final int EXPECTED_RESULT = 28;

        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);
        bowlingGameService.roll(PINS_THIRD_TRY);
        bowlingGameService.roll(PINS_FOURTH_TRY);

        assertEquals(EXPECTED_RESULT, bowlingGameService.getScore());
        assertEquals(3, bowlingGameService.getCurrentFrame());
        assertEquals(1, bowlingGameService.getCurrentRoll());
    }
    @Test
    void givenTenTries_whenIsAStrikeOrSpare_then3RollEnable() {

        int[] rolls = {1, 2, 3, 4, 5, 1, 2, 9, 3, 1, 4, 3, 5, 2, 1, 7, 6, 1, 7, 3, 3};

        for (int i=0; i<rolls.length;i++) {
            bowlingGameService.roll(rolls[i]);
        }
        assertEquals(83, bowlingGameService.getScore());

    }


    @Test
    void givenTenTries_whenIsASpare3Rolled_thenThrowsError() {
        int[] rolls = {1, 2, 3, 4, 5, 1, 2, 9, 3, 1, 4, 3, 5, 2, 1, 7, 6, 3, 7};
        assertGameWithRolls(rolls, 3);
    }

    @Test
    void givenTenTries_whenIsAStrike3Rolled_thenThrowsError() {
        int[] rolls = {1, 2, 3, 4, 5, 1, 2, 9, 3, 1, 4, 3, 5, 2, 1, 7, 6, 3, 7};
        assertGameWithRolls(rolls, 10);
    }

    private void assertGameWithRolls(int[] rolls, int lastRoll) {
        final String EXPECTED_MESSAGE = "It is the tenth frame and your third roll, you can not roll more.";

        for (int i = 0; i < rolls.length; i++) {
            bowlingGameService.roll(rolls[i]);
        }
        BowlingGameException gameException = assertThrows(BowlingGameException.class, () -> {
            bowlingGameService.roll(lastRoll);
        });
        assertEquals(EXPECTED_MESSAGE, gameException.getMessage());

    }

    @Test
    void givenVariousPins_whenUserChooseReset_thenResetGame() {
        final int PINS_FIRST_TRY = 5;
        final int PINS_SECOND_TRY = 10;
        final int EXPECTED_RESULT_SCORE = 0;
        final int EXPECTED_RESULT_FRAME = 1;
        final int EXPECTED_RESULT_ROLL = 1;
        bowlingGameService.roll(PINS_FIRST_TRY);
        bowlingGameService.roll(PINS_SECOND_TRY);

        bowlingGameService.reset();

        assertEquals(EXPECTED_RESULT_FRAME, bowlingGameService.getCurrentFrame());
        assertEquals(EXPECTED_RESULT_ROLL, bowlingGameService.getCurrentRoll());
        assertEquals(EXPECTED_RESULT_SCORE, bowlingGameService.getScore());
    }
}