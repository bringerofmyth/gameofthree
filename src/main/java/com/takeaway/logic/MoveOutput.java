package com.takeaway.logic;

import com.takeaway.domainvalue.Status;
import lombok.Data;
import lombok.Getter;

/**
 * Created by melihgurgah on 17/10/2017.
 */

@Data
public class MoveOutput {

    @Getter
    private int resultNumber;

    @Getter
    private Status resultStatus;

    public MoveOutput(int resultNumber, Status resultStatus) {
        this.resultNumber = resultNumber;
        this.resultStatus = resultStatus;
    }
}
