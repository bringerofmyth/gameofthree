package com.takeaway.service;

import com.takeaway.dataaccessobject.GameRepository;
import com.takeaway.datatransferobject.GameDTO;
import com.takeaway.datatransferobject.JoinRequest;
import com.takeaway.domainvalue.Status;
import com.takeaway.entity.Game;
import com.takeaway.entity.Player;
import com.takeaway.exception.ConstraintsViolationException;
import com.takeaway.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.List;


@Service
@Slf4j
public class DefaultGameService implements GameService {

    private final GameRepository gameRepository;
    private final PlayerService playerService;


    public DefaultGameService(final GameRepository gameRepository, PlayerService playerService) {
        this.gameRepository = gameRepository;
        this.playerService = playerService;
    }

    @Override
    public Game find(Long gameId) throws EntityNotFoundException {
        return findGame(gameId);
    }


    @Override
    @Transactional
    public Game create(GameDTO game) throws ConstraintsViolationException, EntityNotFoundException {

        Player player = playerService.find(game.getCreatedBy());
        Game convertedGame = new Game(game.getName(), player);

        Game newGame;
        try {
            newGame = gameRepository.save(convertedGame);
        } catch (DataIntegrityViolationException e) {
            log.warn("Some constraints are thrown due to game creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return newGame;
    }


    @Override
    public List<Game> list(Status status) {
        return gameRepository.findByStatus(status);
    }

    @Override
    @Transactional
    public void join(JoinRequest request) throws EntityNotFoundException {
        Game game = findGame(request.getGameId());
        Player player = playerService.find(request.getPlayerId());

        game.setPlayer2(player);
        game.setStatus(Status.WAITING_TO_START);
        game.setLastActivity(ZonedDateTime.now());
    }

    private Game findGame(Long gameId) throws EntityNotFoundException {
        Game game = gameRepository.findOne(gameId);
        if (game == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + gameId);
        }
        return game;
    }

}
