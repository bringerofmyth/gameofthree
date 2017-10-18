package com.takeaway.service;

import com.takeaway.datatransferobject.Action;
import com.takeaway.datatransferobject.Message;

/**
 * Created by melihgurgah on 15/10/2017.
 */
public interface GameRuntimeService {
    Message start(Action action);
    Message move(Action action);
}
