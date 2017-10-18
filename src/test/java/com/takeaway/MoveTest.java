package com.takeaway;

import com.takeaway.domainvalue.Status;
import com.takeaway.exception.WrongMoveException;
import com.takeaway.logic.MoveInput;
import com.takeaway.logic.MoveOutput;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by melihgurgah on 18/10/2017.
 */
public class MoveTest {

    private static final int ACTION_ZERO = 0;
    private static final int ACTION_ONE = 1;
    private static final int ACTION_MINUS_ONE = -1;
    private static final int INPUT_NINETY = 90;
    private static final int INPUT_NINETY_ONE = 91;
    private static final int INPUT_EIGHTY_NINE = 89;
    private static final int RESULT_THIRTY = 30;
    private static final int LAST_NUMBER_BEFORE_WIN = 2;
    private static final int WINNING_NUMBER = 1;
    private static final int INVALID_ACTION_NUMBER_HIGHER = 2;
    private static final int INVALID_ACTION_NUMBER_LOWER = -2;
    private static final Status P1_TURN = Status.P1_MOVE;
    private static final Status P2_TURN = Status.P2_MOVE;
    private static final Status PLAYER1_WINS = Status.P1_WINS;
    private static final Status PLAYER2_WINS = Status.P2_WINS;


    @Test
    public void testActionMinusOneForPlayer1() throws Exception {
        MoveInput input = new MoveInput(ACTION_MINUS_ONE, INPUT_NINETY_ONE, P1_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P2_TURN);
    }

    @Test
    public void testActionZeroForPlayer1() throws Exception {
        MoveInput input = new MoveInput(ACTION_ZERO, INPUT_NINETY, P1_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P2_TURN);
    }

    @Test
    public void testActionOneForPlayer1() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, INPUT_EIGHTY_NINE, P1_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P2_TURN);
    }

    @Test
    public void testActionMinusOneForPlayer2() throws Exception {
        MoveInput input = new MoveInput(ACTION_MINUS_ONE, INPUT_NINETY_ONE, P2_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P1_TURN);
    }

    @Test
    public void testActionZeroForPlayer2() throws Exception {
        MoveInput input = new MoveInput(ACTION_ZERO, INPUT_NINETY, P2_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P1_TURN);
    }

    @Test
    public void testActionOneForPlayer2() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, INPUT_EIGHTY_NINE, P2_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), RESULT_THIRTY);
        assertEquals(moveOutput.getResultStatus(), P1_TURN);
    }
    @Test
    public void testWinForPlayer1() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, LAST_NUMBER_BEFORE_WIN, P1_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), WINNING_NUMBER);
        assertEquals(moveOutput.getResultStatus(), PLAYER1_WINS);
    }

    @Test
    public void testWinForPlayer2() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, LAST_NUMBER_BEFORE_WIN, P2_TURN);
        MoveOutput moveOutput = input.move();
        assertEquals(moveOutput.getResultNumber(), WINNING_NUMBER);
        assertEquals(moveOutput.getResultStatus(), PLAYER2_WINS);
    }

    @Test(expected = WrongMoveException.class)
    public void testWrongMove() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, INPUT_NINETY, P1_TURN);
        input.move();
    }

    @Test(expected = WrongMoveException.class)
    public void testWrongMoveOnFinishedGamePlayer1() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, INPUT_EIGHTY_NINE, Status.P1_WINS);
        input.move();
    }
    @Test(expected = WrongMoveException.class)
    public void testWrongMoveOnFinishedGamePlayer2() throws Exception {
        MoveInput input = new MoveInput(ACTION_ONE, INPUT_EIGHTY_NINE, Status.P2_WINS);
        input.move();
    }

    @Test(expected = WrongMoveException.class)
    public void testWrongMoveForHigherActionNumber() throws Exception {
        MoveInput input = new MoveInput(INVALID_ACTION_NUMBER_HIGHER, INPUT_NINETY_ONE , P1_TURN);
        input.move();
    }

    @Test(expected = WrongMoveException.class)
    public void testWrongMoveForLowerActionNumber() throws Exception {
        MoveInput input = new MoveInput(INVALID_ACTION_NUMBER_LOWER, INPUT_EIGHTY_NINE , P1_TURN);
        input.move();
    }


}
