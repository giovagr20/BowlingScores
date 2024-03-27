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
    void givenTenTries_whenIsAStrikeOrSpare3Rolled_thenThrowsError() {
        final int TRY_1 = 1;
        final int TRY_2 = 2;
        final int TRY_3 = 3;
        final int TRY_4 = 4;
        final int TRY_5 = 5;
        final int TRY_6 = 1;
        final int TRY_7 = 2;
        final int TRY_8 = 9;
        final int TRY_9 = 3;
        final int TRY_10 = 1;
        final int TRY_11 = 4;
        final int TRY_12 = 3;
        final int TRY_13 = 5;
        final int TRY_14 = 2;
        final int TRY_15 = 1;
        final int TRY_16 = 7;
        final int TRY_17 = 6;
        final int TRY_18 = 3;
        final int TRY_19 = 7;
        final int TRY_20 = 3;
        final String EXPECTED_MESSAGE = "It is the tenth frame and your third roll, you can not roll more.";
        bowlingGameService.roll(TRY_1);
        bowlingGameService.roll(TRY_2);
        bowlingGameService.roll(TRY_3);
        bowlingGameService.roll(TRY_4);
        bowlingGameService.roll(TRY_5);
        bowlingGameService.roll(TRY_6);
        bowlingGameService.roll(TRY_7);
        bowlingGameService.roll(TRY_8);
        bowlingGameService.roll(TRY_9);
        bowlingGameService.roll(TRY_10);
        bowlingGameService.roll(TRY_11);
        bowlingGameService.roll(TRY_12);
        bowlingGameService.roll(TRY_13);
        bowlingGameService.roll(TRY_14);
        bowlingGameService.roll(TRY_15);
        bowlingGameService.roll(TRY_16);
        bowlingGameService.roll(TRY_17);
        bowlingGameService.roll(TRY_18);
        bowlingGameService.roll(TRY_19);
        BowlingGameException gameException = assertThrows(BowlingGameException.class, () -> {
            bowlingGameService.roll(TRY_20);
        });
        assertEquals(EXPECTED_MESSAGE,"It is the tenth frame and your third roll, you can not roll more.");
        assertEquals(102, bowlingGameService.getScore());
    }

    @Test
    void givenTenTries_whenIsAStrikeOrSpare_then3RollEnable() {
        final int TRY_1 = 1;
        final int TRY_2 = 2;
        final int TRY_3 = 3;
        final int TRY_4 = 4;
        final int TRY_5 = 5;
        final int TRY_6 = 1;
        final int TRY_7 = 2;
        final int TRY_8 = 9;
        final int TRY_9 = 3;
        final int TRY_10 = 1;
        final int TRY_11 = 4;
        final int TRY_12 = 3;
        final int TRY_13 = 5;
        final int TRY_14 = 2;
        final int TRY_15 = 1;
        final int TRY_16 = 7;
        final int TRY_17 = 6;
        final int TRY_18 = 1;
        final int TRY_19 = 7;
        final int TRY_20 = 3;
        final int TRY_21 = 3;
        bowlingGameService.roll(TRY_1);
        bowlingGameService.roll(TRY_2);
        bowlingGameService.roll(TRY_3);
        bowlingGameService.roll(TRY_4);
        bowlingGameService.roll(TRY_5);
        bowlingGameService.roll(TRY_6);
        bowlingGameService.roll(TRY_7);
        bowlingGameService.roll(TRY_8);
        bowlingGameService.roll(TRY_9);
        bowlingGameService.roll(TRY_10);
        bowlingGameService.roll(TRY_11);
        bowlingGameService.roll(TRY_12);
        bowlingGameService.roll(TRY_13);
        bowlingGameService.roll(TRY_14);
        bowlingGameService.roll(TRY_15);
        bowlingGameService.roll(TRY_16);
        bowlingGameService.roll(TRY_17);
        bowlingGameService.roll(TRY_18);
        bowlingGameService.roll(TRY_19);
        bowlingGameService.roll(TRY_20);
        bowlingGameService.roll(TRY_21);
        assertEquals(83, bowlingGameService.getScore());

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