package com.takeaway.controller;

import com.takeaway.controller.mapper.GameMapper;
import com.takeaway.datatransferobject.BaseResponse;
import com.takeaway.datatransferobject.GameDTO;
import com.takeaway.datatransferobject.JoinRequest;
import com.takeaway.datatransferobject.PlayerDTO;
import com.takeaway.domainvalue.Status;
import com.takeaway.entity.Game;
import com.takeaway.entity.Player;
import com.takeaway.exception.ConstraintsViolationException;
import com.takeaway.exception.EntityNotFoundException;
import com.takeaway.service.GameService;
import com.takeaway.service.PlayerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/game")
@Slf4j
public class GameController {

    private final GameService gameService;
    private final PlayerService playerService;

    @Autowired
    public GameController(final GameService gameService, final PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @GetMapping("/{gameId}")
    public GameDTO getGame(@Valid @PathVariable long gameId) throws EntityNotFoundException {
        return GameMapper.makeGameDTO(gameService.find(gameId));
    }


    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public GameDTO createGame(@Valid @RequestBody GameDTO gameDTO) throws ConstraintsViolationException,
            EntityNotFoundException {
        Game game = gameService.create(gameDTO);
        GameDTO response = new GameDTO(game.getId(), game.getName());

        return response;
    }


    @PostMapping("/player")
    @ResponseStatus(HttpStatus.CREATED)
    public PlayerDTO createPlayer(@Valid @RequestBody PlayerDTO playerDTO) throws ConstraintsViolationException,
            EntityNotFoundException {

        log.info("player create request " + playerDTO);
        Player player = playerService.create(playerDTO);
        PlayerDTO response = new PlayerDTO(player.getId(), player.getUsername());

        return response;
    }

    @GetMapping("/list")
    public List<GameDTO> list() {
        return GameMapper.makeGameDTOList(gameService.list(Status.P2_WAITING_TO_JOIN));
    }

    @PostMapping("/join")
    public BaseResponse join(@RequestBody JoinRequest request) throws EntityNotFoundException {
        gameService.join(request);
        return new BaseResponse();
    }


}
