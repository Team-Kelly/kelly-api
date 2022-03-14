package team.kelly.kellyserver.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.mock.dto.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mock")
public class MockApiController {

    @PostMapping("/weather/phrase")
    public Map<String, String> getWeatherPhrase(@RequestBody MockWeatherSearchDto mockWeatherSearchDto) {
        log.info(mockWeatherSearchDto.toString());
        Map<String, String> map = new HashMap<>();
        map.put("phrase", "오늘도 좋은하루 :)");
        return map;
    }

    @PostMapping("/weather/oneday")
    public List<MockWeatherDto> getWeatherDayList(@RequestBody MockWeatherSearchDto mockWeatherSearchDto) {
        log.info(mockWeatherSearchDto.toString());
        return WeatherDataGenerator.getDayWeatherList();
    }

    @PostMapping("/navi/routeList")
    public List<MockRouteDto> getRouteList(@RequestBody MockRouteSearchDto mockRouteSearchDto) {
        log.info(mockRouteSearchDto.toString());
        List<MockRouteDto> mockRouteDtoList = new ArrayList<>();
        mockRouteDtoList.add(RouteDataGenerator.get1());
        mockRouteDtoList.add(RouteDataGenerator.get2());
        mockRouteDtoList.add(RouteDataGenerator.get3());
        mockRouteDtoList.add(RouteDataGenerator.get4());
        mockRouteDtoList.add(RouteDataGenerator.get5());
        mockRouteDtoList.add(RouteDataGenerator.get6());
        mockRouteDtoList.add(RouteDataGenerator.get7());
        mockRouteDtoList.add(RouteDataGenerator.get8());
        mockRouteDtoList.add(RouteDataGenerator.get9());

        return mockRouteDtoList;
    }

}
