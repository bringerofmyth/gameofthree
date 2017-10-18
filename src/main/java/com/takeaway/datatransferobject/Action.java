package com.takeaway.datatransferobject;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by melihgurgah on 17/10/2017.
 */
@Data
public class Action {
    @NotNull
    private Long playerId;

    @NotNull
    private Long gameId;

    private Integer actionValue;

    private Boolean isStart;
}
