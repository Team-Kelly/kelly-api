package team.kelly.kellyserver.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.mock.dto.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mock")
public class MockApiController {

    @PostMapping("/weather/phrase")
    public MockWelcomPhraseDto getWeatherPhrase(@RequestBody MockWeatherSearchDto mockWeatherSearchDto) {
        log.info(mockWeatherSearchDto.toString());
        return new MockWelcomPhraseDto(1, "오늘은 하루종일 화창한 하루!\n좋은 하루 되세요:)");
    }

    @PostMapping("/weather/dayList")
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
