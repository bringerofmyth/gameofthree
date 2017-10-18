package com.takeaway.logic;


import com.takeaway.domainvalue.Status;
import com.takeaway.exception.WrongMoveException;

/**
 * Created by melihgurgah on 17/10/2017.
 */

public class MoveInput {

    private int actionValue;
    private int lastNumber;
    private Status status;

    public MoveInput(int actionValue, int lastNumber, Status status) throws WrongMoveException {
        if (lastNumber <= 1 || actionValue < -1 || actionValue > 1 || (lastNumber + actionValue) % 3 != 0
                || Status.P1_MOVE != status && Status.P2_MOVE != status) {
            throw new WrongMoveException("Wrong move");
        }
        this.actionValue = actionValue;
        this.lastNumber = lastNumber;
        this.status = status;
    }

    public MoveOutput move() throws WrongMoveException {
        Integer lastValue = (actionValue + lastNumber) / 3;
        Status resultStatus;
        if (lastValue == 1) {
            resultStatus = status == Status.P1_MOVE ? Status.P1_WINS : Status.P2_WINS;
        } else {
            resultStatus = status == Status.P1_MOVE ? Status.P2_MOVE : Status.P1_MOVE;
        }
        return new MoveOutput(lastValue, resultStatus);
    }
}
