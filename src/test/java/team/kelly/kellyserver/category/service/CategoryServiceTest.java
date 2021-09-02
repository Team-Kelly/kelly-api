package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.kelly.kellyserver.category.dto.*;

import java.io.IOException;
import java.text.ParseException;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;
    @InjectMocks
    BusService busService;
    @InjectMocks
    SubwayService subwayService;
    @InjectMocks
    WeatherService weatherService;

    @Test
    void getBusArriveData_잘_작동되는지() throws IOException {

        BusSearchInfoDto given = new BusSearchInfoDto();
        given.setBusNumber("261");
        given.setStationNumber("11457");
        BusResultInfoDto actual = busService.getBusArriveData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();
    }

    @Test
    void getSubwayArriveData_잘_작동되는지() throws IOException, ParseException {

        SubwaySearchInfoDto given = new SubwaySearchInfoDto();
        given.setSubwayId("1004");
        given.setStatnNm("서울");
        given.setUpdnLine("하행");
        SubwayResultInfoDto actual = subwayService.getSubwayArriveData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }

    @Test
    void getCurrentWeatherData_잘_작동되는지() throws IOException {

        WeatherSearchInfoDto given = new WeatherSearchInfoDto();
        given.setLat(65);
        given.setLon(129);
        WeatherResultInfoDto actual = weatherService.getCurrentWeatherData(given);

        log.info(actual.toString());
        assertThat(actual).isNotNull();

    }
}