package team.kelly.kellyserver.category.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.category.dto.*;
import team.kelly.kellyserver.category.service.BusService;
import team.kelly.kellyserver.category.service.SubwayService;
import team.kelly.kellyserver.category.service.WeatherLegacyService;
import team.kelly.kellyserver.category.service.WeatherService;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final BusService busService;
    private final SubwayService subwayService;
    private final WeatherLegacyService weatherLegacyService;
    private final WeatherService weatherService;

    @PostMapping(path = "/bus/arrive")
    @ApiOperation(value = "버스 도착 정보 api",
            notes = "실시간 버스 도착까지 남은 시간을 받아온다.")
    public BusResultDto getBusArriveData(@RequestBody BusSearchDto infoVO) throws IOException {
        return busService.getBusArriveData(infoVO);
    }

    @PostMapping(path = "/subway/arrive")
    @ApiOperation(value = "지하철 도착 정보 api",
            notes = "실시간 지하철 도착까지 남은 시간을 받아온다.")
    public SubwayResultDto getSubwayArriveData(@RequestBody SubwaySearchDto infoVO) throws IOException, ParseException {
        return subwayService.getSubwayArriveData(infoVO);
    }

    @Deprecated
    @PostMapping(path = "/legacy/weather/oneday")
    @ApiOperation(value = "24시간 날씨 정보 api",
            notes = "지금으로부터 +24(~33)시간동안의 기온, 하늘상태설명/코드, 강수상태설명/코드, 날씨상태설명/코드, 강수확률을 받아온다.\n" +
                    "skyStatus(하늘상태) : 맑음(1), 구름많음(3), 흐림(4)\n" +
                    "rainStatus(강수상태) : 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4), 빗방울(5), 빗방울눈날림(6), 눈날림(7)\n" +
                    "weatherStatus(날씨상태) : 맑음(0), 구름많음(1), 흐림(2), 비(3), 비/눈(4), 눈(5), 소나기(6)")
    public WeatherResultLegacyDto getOneDayWeatherLegacyData(@RequestBody WeatherSearchDto infoVO) throws IOException {
        return weatherLegacyService.getOneDayWeatherLegacyData(infoVO);
    }

    @PostMapping(path = "/weather/oneday")
    @ApiOperation(value = "24시간 날씨 정보 api",
            notes = "지금으로부터 +24(~48)시간동안의 기온, 날씨상태설명/코드, 강수확률을 받아온다.\n" +
                    "weatherStatus(날씨상태) : 맑음(0), 구름조금(1), 구름많음(2), 비(3), 천둥(4), 눈(5), 흐림(6)")
    public List<WeatherResultDto> getOneDayWeatherData(@RequestBody WeatherSearchDto infoVO) throws IOException {
        return weatherService.getOneDayWeatherData(infoVO);
    }
}
