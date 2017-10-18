package com.takeaway.datatransferobject;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GameDTO {
    private Long id;

    @NotNull(message = "Name can not be null!")
    private String name;

    private Long createdBy;

    public GameDTO() {
    }

    public GameDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}
