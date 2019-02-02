package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.model.TournamentSeasonModel;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.db.service.TournamentSeasonService;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TournamentSeasonController {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private TournamentSeasonService seasonService;

    @PostMapping(path = "/tournament/{tournamentId}/season")
    public TournamentSeasonModel createTournamentSeason(
            @PathVariable Integer tournamentId,
            @RequestBody TournamentSeasonModel seasonModel
    ) throws ValidationException {
        TournamentSeason season = dozerBeanMapper.map(seasonModel, TournamentSeason.class);
        season = seasonService.save(season, tournamentId);

        return dozerBeanMapper.map(season, TournamentSeasonModel.class);
    }

    @GetMapping(path = "/tournament/{tournamentId}/seasons")
    public List<TournamentSeasonModel> getAllTournamentSeasons(@PathVariable Integer tournamentId) {
        List<TournamentSeason> seasons = seasonService.findByTournamentId(tournamentId);
        // FIXME Can't map list to list using dozerBeanMapper.
        //  There's raised exception with LocalDateTime field related.
        return seasons.stream()
                .map(s -> dozerBeanMapper.map(s, TournamentSeasonModel.class))
                .collect(Collectors.toList());
    }

}
