package com.takeaway.service;

import com.takeaway.datatransferobject.PlayerDTO;
import com.takeaway.entity.Player;
import com.takeaway.exception.ConstraintsViolationException;
import com.takeaway.exception.EntityNotFoundException;

public interface PlayerService {
    Player create(PlayerDTO player) throws ConstraintsViolationException;
    Player find(Long id) throws EntityNotFoundException;
}
