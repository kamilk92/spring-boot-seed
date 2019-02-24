package pl.kkp.core.controller;


import org.dozer.DozerBeanMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.model.TournamentModel;
import pl.kkp.core.db.entity.Tournament;
import pl.kkp.core.db.service.TournamentService;
import pl.kkp.core.db.service.exception.EntityWithIdNotFoundException;
import pl.kkp.core.db.service.validate.exception.ValidationException;

import javax.transaction.NotSupportedException;
import java.util.LinkedList;
import java.util.List;

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

    @GetMapping(path = "/tournament/{id}")
    public TournamentModel getTournamentById(@PathVariable Integer id) throws EntityWithIdNotFoundException {
        Tournament tournament = tournamentService.findById(id);

        return dozerBeanMapper.map(tournament, TournamentModel.class);
    }

    @GetMapping(path = "/tournaments")
    public List<TournamentModel> getAllTournaments() throws NotSupportedException {
        Iterable<Tournament> tournaments = tournamentService.findAll();
        List<TournamentModel> tournamentModels = new LinkedList<>();
        dozerBeanMapper.map(tournaments, tournamentModels);

        return tournamentModels;
    }

}
