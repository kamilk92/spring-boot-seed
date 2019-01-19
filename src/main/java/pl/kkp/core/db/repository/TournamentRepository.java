package pl.kkp.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.entity.Tournament;

public interface TournamentRepository extends CrudRepository<Tournament, Integer> {
}
