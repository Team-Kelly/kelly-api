package team.kelly.kellyserver.navigation.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.navigation.dto.RouteDto;
import team.kelly.kellyserver.navigation.dto.RouteSearchDto;
import team.kelly.kellyserver.navigation.service.NavigationService;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/navi")
@RequiredArgsConstructor
public class NavigationController {

    private final NavigationService navigationService;

    @PostMapping("/route")
    @ApiOperation(value = "길찾기 api",
            notes = "출발지-도착지까지의 전체 경로를 탐색한다.\n" +
                    "option(경로를 필터링) : 전체(0), 지하철(1), 버스(2)\n" +
                    "버스 정보의 경우 busId, cityCode, (start)stationId, \n" +
                    "지하철 정보의 경우 direction, lineId, stationName 정보의 저장이 필요하다.")
    public List<RouteDto> getNavigationRoute(@RequestBody RouteSearchDto routeSearchDto) throws IOException {
        return navigationService.getNavigationRuote(routeSearchDto);
    }

}
