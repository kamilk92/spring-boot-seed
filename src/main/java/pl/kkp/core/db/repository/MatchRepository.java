package pl.kkp.core.db.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.kkp.core.db.entity.TournamentMatch;

import java.util.List;

public interface MatchRepository extends CrudRepository<TournamentMatch, Integer> {

    List<TournamentMatch> findAllByTournamentSeasonId(int id);

    @Modifying(clearAutomatically = true)
    @Query(
            "update TournamentMatch match " +
                    "set match.homeScore=:homeScore, match.awayScore=:awayScore " +
                    "where match.id=:matchId"
    )
    void updateMatchResult(
            @Param("matchId") Integer matchId,
            @Param("homeScore") Integer homeScore,
            @Param("awayScore") Integer awayScore
    );
}
