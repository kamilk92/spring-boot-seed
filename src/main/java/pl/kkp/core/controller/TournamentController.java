package pl.kkp.core.controller;


import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.TournamentService;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@RestController
public class TournamentController {

    @Autowired
    private TournamentService tournamentService;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @PostMapping(path = "/tournament")
    public TournamentModel createTournament(@RequestBody TournamentModel tournamentModel) throws ValidationException {
        Tournament tournament = dozerBeanMapper.map(tournamentModel, Tournament.class);
        tournament = tournamentService.save(tournament);

        return dozerBeanMapper.map(tournament, TournamentModel.class);
    }

}
