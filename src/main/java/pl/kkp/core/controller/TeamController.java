package pl.kkp.core.controller;

import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.kkp.core.controller.model.TeamModel;
import pl.kkp.core.db.entity.Team;
import pl.kkp.core.db.service.TeamService;
import pl.kkp.core.db.service.validate.exception.ValidationException;

@RestController
@RequestMapping(path = "/team")
public class TeamController {

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private TeamService teamService;


    @PostMapping
    public Team saveNewTeam(@RequestBody TeamModel team) throws ValidationException {
        Team teamToSave = dozerBeanMapper.map(team, Team.class);

        return teamService.save(teamToSave);
    }

}
