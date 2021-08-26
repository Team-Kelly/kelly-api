package team.kelly.kellyserver.category.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.category.dto.BusSearchInfoDto;
import team.kelly.kellyserver.category.dto.SubwaySearchInfoDto;
import team.kelly.kellyserver.category.dto.WeatherSearchInfoDto;
import team.kelly.kellyserver.category.service.CategoryService;

@Slf4j
@RestController
@RequestMapping(value = "/api/category", produces = "application/text; charset=utf8")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

//    @PostMapping(path = "/{user}/{category}")
//    @ApiOperation(value = "커스텀 카테고리 api 호출", notes = "커스텀하게 만들어진 카테고리 api를 통해 데이터를 받아온다.")
//    public ResponseEntity<String> getCategoryInfo(@PathVariable String user, @PathVariable String category, @RequestBody CategorySearchInfoDto infoVO) {
//        return ResponseEntity.ok(categoryService.getCategoryResult(user, category, infoVO));
//    }

    @PostMapping(path = "/busarrive")
    @ApiOperation(value = "버스 도착 정보 api 호출", notes = "실시간 버스 도착까지 남은 시간을 받아온다.")
    public ResponseEntity<String> getBusInfo(@RequestBody BusSearchInfoDto infoVO) {
        return ResponseEntity.ok(categoryService.getBusArriveData(infoVO));
    }

    @PostMapping(path = "/subwayarrive")
    @ApiOperation(value = "지하철 도착 정보 api 호출", notes = "실시간 지하철 도착까지 남은 시간을 받아온다.")
    public ResponseEntity<String> getSubwayInfo(@RequestBody SubwaySearchInfoDto infoVO) {
        return ResponseEntity.ok(categoryService.getSubwayArriveData(infoVO));
    }

    @PostMapping(path = "/weather/current")
    @ApiOperation(value = "현재 날씨 정보 api 호출", notes = "현재 날씨의 기온, 습도, 하늘상태, 강수형태를 받아온다.")
    public ResponseEntity<String> getWeatherInfo(@RequestBody WeatherSearchInfoDto infoVO) {
        return ResponseEntity.ok(categoryService.getCurrentWeatherData(infoVO));
    }
//
//    @PostMapping(path = "/stock")
//    @ApiOperation(value="현시간 주식 정보 api 호출", notes="실시간으로 특정 종목의 주식 데이터를 받아온다.")
//    public ResponseEntity<String> getCategoryInfo(@RequestBody CategorySearchInfoDto infoVO){
//        return ResponseEntity.ok(categoryService.getCategoryResult(user, category, infoVO));
//    }
//
//    @PostMapping(path = "/weather")
//    @ApiOperation(value="현시간 날씨 정보 정보 api 호출", notes="특정 지역의 실시간 날씨 정보를 받아온다.")
//    public ResponseEntity<String> getCategoryInfo(@RequestBody CategorySearchInfoDto infoVO){
//        return ResponseEntity.ok(categoryService.getCategoryResult(user, category, infoVO));
//    }
}
