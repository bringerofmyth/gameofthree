package com.takeaway.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Data
@Entity
@Table(
        name = "player",
        uniqueConstraints = @UniqueConstraint(name = "uc_username", columnNames = {"username"})
)
public class Player {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    private String username;

    private Player()
    {
    }

    public Player(String username) {
        this.username = username;
    }

}
