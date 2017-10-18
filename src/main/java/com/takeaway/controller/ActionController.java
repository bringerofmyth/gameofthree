package com.takeaway.controller;

import com.takeaway.datatransferobject.Action;
import com.takeaway.datatransferobject.Message;
import com.takeaway.service.GameRuntimeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.util.Random;

/**
 * Created by melihgurgah on 16/10/2017.
 */
@Slf4j
@Controller
public class ActionController {

    private final GameRuntimeService gameRuntimeService;

    private static final int MIN_START_VALUE = 10000;
    private static final int MAX_START_VALUE = 100000;

    @Autowired
    public ActionController(final GameRuntimeService gameRuntimeService) {
        this.gameRuntimeService = gameRuntimeService;
    }

    @MessageMapping("/room/{room}")
    @SendTo("/topic/move/{room}")
    public Message play(Action action, @DestinationVariable Long room) throws Exception {
        Thread.sleep(1000); // simulated delay
        log.info("Receiving action: " + action);
        Action startAction = new Action();
        startAction.setGameId(action.getGameId());
        startAction.setPlayerId(action.getPlayerId());
        startAction.setIsStart(action.getIsStart());
        startAction.setActionValue(action.getActionValue());
        Message message;

        if (action.getIsStart() != null && action.getIsStart()) {
            Random random = new Random();
            int randomNum = random.nextInt(MAX_START_VALUE) % (MAX_START_VALUE - MIN_START_VALUE + 1) + MIN_START_VALUE;
            startAction.setActionValue(randomNum);
            message = gameRuntimeService.start(startAction);
        } else {
            message = gameRuntimeService.move(startAction);
        }

        log.info("Game message: " + message);

        return message;
    }


}
