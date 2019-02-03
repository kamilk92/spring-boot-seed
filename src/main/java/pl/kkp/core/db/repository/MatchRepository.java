package pl.kkp.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.entity.TournamentMatch;

import java.util.List;

public interface MatchRepository extends CrudRepository<TournamentMatch, Integer> {

    List<TournamentMatch> findAllByTournamentSeasonId(int id);

}
