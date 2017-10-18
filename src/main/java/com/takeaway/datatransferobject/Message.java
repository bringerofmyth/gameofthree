package com.takeaway.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.takeaway.domainvalue.Status;
import lombok.Data;

/**
 * Created by melihgurgah on 16/10/2017.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Message {
    private Integer resultNumber;
    private Status status;
    private String gameMessage;
    private String errorMessage;

    public Message(String errorMessage) {
        this.errorMessage = errorMessage;
        this.gameMessage = errorMessage;
    }

    public Message() {
    }
}
