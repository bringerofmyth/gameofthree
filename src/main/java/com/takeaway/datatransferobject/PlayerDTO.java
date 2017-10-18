package com.takeaway.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PlayerDTO {
    private Long id;

    @NotNull(message = "Name can not be null!")
    private String username;


    public PlayerDTO() {
    }

    public PlayerDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
