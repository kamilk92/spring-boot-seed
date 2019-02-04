package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.mapping.IterableBeanMapper;
import pl.kkp.core.controller.model.TournamentMatchModel;
import pl.kkp.core.db.entity.TournamentMatch;
import pl.kkp.core.db.service.TournamentMatchService;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import javax.transaction.NotSupportedException;
import java.util.List;

@RestController
public class TournamentMatchController {

    @Autowired
    private IterableBeanMapper iterableMapper;

    @Autowired
    private DozerBeanMapper mapper;

    @Autowired
    private TournamentMatchService matchService;

    @PostMapping(path = "/season/{seasonId}/match")
    public TournamentMatchModel createTournamentMatch(
            @PathVariable Integer seasonId, @RequestBody TournamentMatchModel matchModel) throws ValidationException {
        TournamentMatch match = mapper.map(matchModel, TournamentMatch.class);
        match = matchService.save(match, seasonId);

        return mapper.map(match, TournamentMatchModel.class);
    }

    @GetMapping(path = "/matches")
    public List<TournamentMatchModel> getAllMatches() {
        Iterable<TournamentMatch> matches = matchService.findAll();

        return iterableMapper.map(matches, TournamentMatchModel.class);
    }

    @GetMapping(path = "/season/{seasonId}/matches")
    public List<TournamentMatchModel> getAllTournamentSeasonMatches(@PathVariable Integer seasonId) throws NotSupportedException {
        List<TournamentMatch> matches = matchService.findByTournamentSeasonId(seasonId);

        return iterableMapper.map(matches, TournamentMatchModel.class);
    }

    @PutMapping(path = "/match/{id}")
    public TournamentMatchModel updateMatch(@PathVariable Integer id, @RequestBody TournamentMatchModel matchModel)
            throws ValidationException {
        TournamentMatch match = mapper.map(matchModel, TournamentMatch.class);
        match = matchService.updateMatchResult(match);

        return mapper.map(match, TournamentMatchModel.class);
    }

}
