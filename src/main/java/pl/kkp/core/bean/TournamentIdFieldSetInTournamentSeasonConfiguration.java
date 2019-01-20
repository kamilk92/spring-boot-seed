package pl.kkp.core.bean;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kkp.core.db.service.validate.ValidatorActionType;
import pl.kkp.core.db.service.validate.action.TournamentIdFieldSetInTournamentSeason;

import java.util.Arrays;
import java.util.List;

@Configuration
public class TournamentIdFieldSetInTournamentSeasonConfiguration {

    @Bean
    public TournamentIdFieldSetInTournamentSeasonFactory tournamentIdFieldSetInTournamentSeasonFactory() {
        return new TournamentIdFieldSetInTournamentSeasonFactory();
    }

    @Bean
    public TournamentIdFieldSetInTournamentSeason tournamentIdFieldSetInTournamentSeason(
            TournamentIdFieldSetInTournamentSeasonFactory factory) throws Exception {
        return factory.getObject();
    }
}
