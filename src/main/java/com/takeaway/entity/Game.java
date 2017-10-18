package com.takeaway.entity;

import com.takeaway.domainvalue.Status;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(
        name = "game",
        uniqueConstraints = @UniqueConstraint(name = "uc_name", columnNames = {"name"})
)
@Data
public class Game {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime dateCreated = ZonedDateTime.now();

    @Column(nullable = false)
    @NotNull(message = "Name can not be null!")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    @Column
    private Integer lastNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player1_id")
    private Player player1;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "player2_id")
    private Player player2;


    @Column(nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private ZonedDateTime lastActivity = ZonedDateTime.now();

    private Game() {
    }

    public Game(String name, Player player1) {
        this.name = name;
        this.player1 = player1;
        this.status = Status.P2_WAITING_TO_JOIN;
    }
}
