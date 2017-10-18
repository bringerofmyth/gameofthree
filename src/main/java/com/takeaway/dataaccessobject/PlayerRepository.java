package com.takeaway.dataaccessobject;

import com.takeaway.entity.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Long> {

}
