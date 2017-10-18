package com.takeaway.service;

import com.takeaway.datatransferobject.GameDTO;
import com.takeaway.datatransferobject.JoinRequest;
import com.takeaway.domainvalue.Status;
import com.takeaway.entity.Game;
import com.takeaway.exception.ConstraintsViolationException;
import com.takeaway.exception.EntityNotFoundException;

import java.util.List;

public interface GameService {

    Game find(Long gameId) throws EntityNotFoundException;

    Game create(GameDTO game) throws ConstraintsViolationException, EntityNotFoundException;

    List<Game> list(Status status);

    void join(JoinRequest request) throws EntityNotFoundException;
}
