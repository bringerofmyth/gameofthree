package com.takeaway.service;

import com.takeaway.datatransferobject.Action;
import com.takeaway.datatransferobject.Message;
import com.takeaway.domainvalue.Status;
import com.takeaway.entity.Game;
import com.takeaway.exception.*;
import com.takeaway.logic.MoveInput;
import com.takeaway.logic.MoveOutput;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Service
@Slf4j
public class DefaultGameRuntimeService implements GameRuntimeService {

    private final GameService gameService;

    public DefaultGameRuntimeService(final GameService gameService) {
        this.gameService = gameService;
    }

    @Override
    @Transactional
    public Message start(Action action) {

        Game game;
        try {
            game = gameService.find(action.getGameId());
        } catch (EntityNotFoundException e) {
            return new Message(e.getMessage());
        }
        try {
            validateStart(game, action.getPlayerId());
        } catch (GameAlreadyStartedException | InvalidInputException e) {
            return new Message(e.getMessage());
        }

        game.setStatus(game.getPlayer1().getId().equals(action.getPlayerId()) ? Status.P2_MOVE : Status.P1_MOVE);
        game.setLastNumber(action.getActionValue());
        game.setLastActivity(ZonedDateTime.now());

        return convertMoveToMessage(game, action, true);
    }

    @Override
    @Transactional
    public Message move(Action action) {

        Game game;
        try {
            game = gameService.find(action.getGameId());
        } catch (EntityNotFoundException e) {
            return new Message(e.getMessage());
        }
        try {
            validateMove(game, action.getActionValue(), action.getPlayerId());
        } catch (WrongMoveException | NotYourTurnException e) {
            return new Message(e.getMessage());
        }
        MoveOutput moveOutput;

        try {
            MoveInput moveInput = new MoveInput(action.getActionValue(), game.getLastNumber(), game.getStatus());
            moveOutput = moveInput.move();
        } catch (WrongMoveException e) {
            return new Message(e.getMessage());
        }

        game.setStatus(moveOutput.getResultStatus());
        game.setLastNumber(moveOutput.getResultNumber());
        game.setLastActivity(ZonedDateTime.now());

        return convertMoveToMessage(game, action, false);
    }

    private void validateMove(Game game, Integer numberAction, Long attemptedBy) throws WrongMoveException,
            NotYourTurnException {

        if (!(Status.P1_MOVE == game.getStatus() || Status.P2_MOVE == game.getStatus())) {
            throw new WrongMoveException("Action disabled.");
        } else if (Status.P1_MOVE == game.getStatus() && !attemptedBy.equals(game.getPlayer1().getId())) {
            throw new NotYourTurnException("Not your turn.");
        } else if (Status.P2_MOVE == game.getStatus() && !attemptedBy.equals(game.getPlayer2().getId())) {
            throw new NotYourTurnException("Not your turn.");
        }

        boolean isResultValid = (game.getLastNumber() + numberAction) % 3 == 0;

        if (!isResultValid) {
            throw new WrongMoveException("Invalid number action.");
        }

    }

    private void validateStart(Game game, Long requesterId) throws GameAlreadyStartedException, InvalidInputException {
        if (Status.WAITING_TO_START != game.getStatus()) {
            throw new GameAlreadyStartedException("Game already started.");
        }
        if (!(game.getPlayer1().getId().equals(requesterId) || game.getPlayer2().getId().equals(requesterId))) {
            throw new InvalidInputException("Invalid player to start.");
        }
    }

    private static Message convertMoveToMessage(Game game, Action action, boolean isStart) {
        Message message = new Message();
        Status status = game.getStatus();

        message.setStatus(status);
        message.setResultNumber(game.getLastNumber());

        String firstString = "";
        String secondString = "";
        if (Status.P1_MOVE.equals(status)) {
            firstString = "Player 2";
            secondString = "Awaiting move from Player 1";
        } else if (Status.P2_MOVE.equals(status)) {
            firstString = "Player 1";
            secondString = "Awaiting move from Player 2";
        } else if (Status.P2_WINS.equals(status)) {
            firstString = "Player 2";
            secondString = "Player 2 wins";
        } else if (Status.P1_WINS.equals(status)) {
            firstString = "Player 1";
            secondString = "Player 1 wins";
        }

        String builder;

        if (!isStart) {
            builder = firstString + " moved with " + action.getActionValue() + ", and the result is  " +
                    game.getLastNumber() + ". " + secondString;
        } else {
            builder = "The game has been started. The initial number is " + game.getLastNumber() + ". " + secondString;
        }

        message.setGameMessage(builder);

        return message;
    }

}
