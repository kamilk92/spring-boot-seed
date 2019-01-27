package pl.kkp.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.entity.Team;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    Team findByName(String name);
}
