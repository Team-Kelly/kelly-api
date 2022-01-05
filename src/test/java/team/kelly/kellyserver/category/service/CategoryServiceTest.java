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

        WeatherGeoSearchDto given = new WeatherGeoSearchDto();
        given.setLat(37.5132612);
        given.setLon(127.0979449);
        WeatherCurResultDto actual = weatherService.getCurrentWeatherData(given);

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

    @Test
    void getWeekWeatherData_잘_작동되는지() throws IOException {

        WeatherGeoSearchDto given = new WeatherGeoSearchDto();
        given.setLat(37.5132612);
        given.setLon(127.0979449);
        WeatherDivSearchDto given2 = new WeatherDivSearchDto();
        given2.setAdminDivision("경기도 남양주시");
        WeatherMidResultDto actual = weatherService.getWeekWeatherData(given2, given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getWeekTaWeatherData_잘_작동되는지() throws IOException {

        WeatherMidResultDto actual = new WeatherMidResultDto();
        actual = weatherService.getWeekTaWeatherData("11B10101", actual);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getWeekLandWeatherData_잘_작동되는지() throws IOException {

        WeatherMidResultDto actual = new WeatherMidResultDto();
        actual = weatherService.getWeekLandWeatherData("11B00000", actual);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }
}