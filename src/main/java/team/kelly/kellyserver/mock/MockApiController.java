package team.kelly.kellyserver.mock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.mock.dto.MockRouteDto;
import team.kelly.kellyserver.mock.dto.MockRouteSearchDto;
import team.kelly.kellyserver.mock.dto.MockWelcomPhraseDto;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/mock")
public class MockApiController {

    @GetMapping("/welcomPhrase")
    public MockWelcomPhraseDto getWelcomePhrase() {
        return new MockWelcomPhraseDto(1, "아침부터 비를 맞았네..\n어제는 날씨 좋았잖아..\n시작이 반인데..!");
    }

    @PostMapping("/routeList")
    public List<MockRouteDto> getRouteList(MockRouteSearchDto mockRouteSearchDto) {
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
