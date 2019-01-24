package pl.kkp.core.controller.model;

import org.dozer.DozerBeanMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.kkp.core.bean.LocalDateTimeParserConfiguration;
import pl.kkp.core.db.entity.TournamentSeason;
import pl.kkp.core.testing.SpringBootBaseTest;
import pl.kkp.core.util.date.LocalDateTimeParser;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class TestMapTournamentSeasonModelToTournamentSeason extends SpringBootBaseTest {
    private DateTimeFormatter dateTimeFormatter;

    @Autowired
    private DozerBeanMapper dozerBeanMapper;

    @Autowired
    private LocalDateTimeParser localDateTimeParser;

    public TestMapTournamentSeasonModelToTournamentSeason() {
        dateTimeFormatter = DateTimeFormatter.ofPattern(LocalDateTimeParserConfiguration.DATE_FMT);
    }

    @Test
    public void testMapTournamentSeasonModelToTournamentSeason() throws ParseException {
        LocalDateTime beginDate = localDateTimeParser.parse("24-01-2019 14:00:00");
        TournamentSeasonModel seasonModel = new TournamentSeasonModel(beginDate);

        TournamentSeason season = dozerBeanMapper.map(seasonModel, TournamentSeason.class);

        assertThat(season).isNotNull();
        assertThat(season.getBeginDate()).isNotNull();
        assertThat(season.getBeginDate()).isEqualTo(seasonModel.getBeginDate());
    }

    @Test
    public void isMapTournamentSeasonToTournamentSeasonModel() throws ParseException {
        LocalDateTime beginDate = LocalDateTime.parse("24-01-2019 15:43:00", dateTimeFormatter);
        TournamentSeason season = new TournamentSeason(beginDate);

        TournamentSeasonModel seasonModel = dozerBeanMapper.map(season, TournamentSeasonModel.class);

        assertThat(seasonModel).isNotNull();
        assertThat(seasonModel.getBeginDate()).isNotNull();
        assertThat(season.getBeginDate()).isEqualTo(seasonModel.getBeginDate());
    }
}
