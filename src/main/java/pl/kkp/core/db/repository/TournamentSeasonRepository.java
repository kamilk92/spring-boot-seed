package pl.kkp.core.db.repository;

import org.springframework.data.repository.CrudRepository;
import pl.kkp.core.db.entity.TournamentSeason;

import java.util.List;

public interface TournamentSeasonRepository extends CrudRepository<TournamentSeason, Integer> {

    List<TournamentSeason> findByTournamentId(Integer tournamentId);
}
