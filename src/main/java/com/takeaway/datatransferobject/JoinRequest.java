package com.takeaway.datatransferobject;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * Created by melihgurgah on 16/10/2017.
 */
@Data
public class JoinRequest {
    @NotNull
    private Long playerId;

    @NotNull
    private Long gameId;
}
