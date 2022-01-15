package team.kelly.kellyserver.navigation.controller;

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
    public List<RouteDto> getNavigationRoute(@RequestBody RouteSearchDto routeSearchDto) throws IOException {
        return navigationService.getNavigationRuote(routeSearchDto);
    }

}
