package team.kelly.kellyserver.admin;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.common.GlobalValue;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class AdminController {

    private final GlobalValue globalValue;

    @GetMapping("/setWeatherApiCallCount")
    @ApiOperation(value = "open weather api 호출 횟수 설정")
    public ResponseEntity setWeatherApiCallCount(@RequestParam(required = true) int count) {

        globalValue.setWeatherApiCallCount(count);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/setOdsayApiCallCount")
    @ApiOperation(value = "odsay api 호출 횟수 설정")
    public ResponseEntity setOdsayApiCallCount(@RequestParam(required = true) int count) {

        globalValue.setOdsayApiCallCount(count);

        return ResponseEntity.ok("success");
    }

    @GetMapping("/getWeatherApiCallCount")
    @ApiOperation(value = "open weather api 호출 횟수 설정")
    public ResponseEntity getWeatherApiCallCount() {
        return ResponseEntity.ok(globalValue.getWeatherApiCallCount());
    }

    @GetMapping("/getOdsayApiCallCount")
    @ApiOperation(value = "odsay api 호출 횟수 확인")
    public ResponseEntity getOdsayApiCallCount() {
        return ResponseEntity.ok(globalValue.getOdsayApiCallCount());
    }

}
