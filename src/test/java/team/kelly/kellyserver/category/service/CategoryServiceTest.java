package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.kelly.kellyserver.category.dto.*;

import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
class CategoryServiceTest {

    @Autowired
    BusService busService;
    @Autowired
    SubwayService subwayService;
    @Autowired
    WeatherService weatherService;

    @Test
    void getBusArriveData_잘_작동되는지() throws IOException {

        BusSearchDto given = new BusSearchDto();
        given.setBusNumber("261");
        given.setStationNumber("11457");
        BusResultDto actual = busService.getBusArriveData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();
    }

    @Test
    void getSubwayArriveData_잘_작동되는지() throws IOException, ParseException {

        SubwaySearchDto given = new SubwaySearchDto();
        given.setSubwayId("1004");
        given.setStatnNm("서울");
        given.setUpdnLine("하행");
        SubwayResultDto actual = subwayService.getSubwayArriveData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getCurrentWeatherData_잘_작동되는지() throws IOException {

        WeatherSearchDto given = new WeatherSearchDto();
        given.setLat(37.5132612);
        given.setLon(127.0979449);
        WeatherResultDto actual = weatherService.getCurrentWeatherData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getVilageUltraFcstWeatherData_잘_작동되는지() throws IOException {

        GridValueDto given = new GridValueDto();
        given.setGridx(61);
        given.setGridy(125);
        WeatherUltraCurDto actual = weatherService.getVilageUltraFcstWeatherData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getVilageFcstWeatherData_잘_작동되는지() throws IOException {

        GridValueDto given = new GridValueDto();
        given.setGridx(61);
        given.setGridy(125);
        WeatherCurDto actual = weatherService.getVilageFcstWeatherData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }
}