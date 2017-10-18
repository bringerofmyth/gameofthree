package com.takeaway.dataaccessobject;

import com.takeaway.entity.Game;
import com.takeaway.domainvalue.Status;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface GameRepository extends CrudRepository<Game, Long> {

    List<Game> findByStatus(Status status);
}
