package com.takeaway.service;

import com.takeaway.dataaccessobject.PlayerRepository;
import com.takeaway.datatransferobject.PlayerDTO;
import com.takeaway.entity.Player;
import com.takeaway.exception.ConstraintsViolationException;
import com.takeaway.exception.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DefaultPlayerService implements PlayerService {

    private final PlayerRepository playerRepository;

    public DefaultPlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }


    /**
     * Creates a new player.
     *
     * @param player
     * @return
     * @throws ConstraintsViolationException if a player already exists with the given username.
     */
    @Override
    public Player create(PlayerDTO player) throws ConstraintsViolationException {
        Player newPlayer;
        try {

            newPlayer = playerRepository.save(new Player(player.getUsername()));
        } catch (DataIntegrityViolationException e) {
            log.warn("Some constraints are thrown due to player creation", e);
            throw new ConstraintsViolationException(e.getMessage());
        }
        return newPlayer;
    }

    @Override
    public Player find(Long id) throws EntityNotFoundException {
        return findPlayer(id);
    }

    private Player findPlayer(Long playerId) throws EntityNotFoundException {
        Player player = playerRepository.findOne(playerId);
        if (player == null) {
            throw new EntityNotFoundException("Could not find entity with id: " + playerId);
        }
        return player;
    }

}
