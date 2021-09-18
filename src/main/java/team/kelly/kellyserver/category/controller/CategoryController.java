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
import team.kelly.kellyserver.category.service.WeatherService;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@RestController
@RequestMapping(value = "/api/category")
@RequiredArgsConstructor
public class CategoryController {

    private final BusService busService;
    private final SubwayService subwayService;
    private final WeatherService weatherService;

    @PostMapping(path = "/busarrive")
    @ApiOperation(value = "버스 도착 정보 api 호출", notes = "실시간 버스 도착까지 남은 시간을 받아온다.")
    public BusResultDto getBusArriveData(@RequestBody BusSearchDto infoVO) throws IOException {
        return busService.getBusArriveData(infoVO);
    }

    @PostMapping(path = "/subwayarrive")
    @ApiOperation(value = "지하철 도착 정보 api 호출", notes = "실시간 지하철 도착까지 남은 시간을 받아온다.")
    public SubwayResultDto getSubwayArriveData(@RequestBody SubwaySearchDto infoVO) throws IOException, ParseException {
        return subwayService.getSubwayArriveData(infoVO);
    }

    @PostMapping(path = "/weather/current")
    @ApiOperation(value = "현재 날씨 정보 api 호출", notes = "현재 날씨의 기온, 하늘상태, 강수형태, 최고기온, 최저기온, 강수확률을 받아온다.")
    public WeatherResultDto getCurrentWeatherData(@RequestBody WeatherSearchDto infoVO) throws IOException {
        return weatherService.getCurrentWeatherData(infoVO);
    }
}
