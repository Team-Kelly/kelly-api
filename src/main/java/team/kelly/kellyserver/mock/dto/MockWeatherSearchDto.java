package team.kelly.kellyserver.mock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MockWeatherSearchDto {
    @ApiModelProperty(required = true, value = "위도", example = "37.6576769")
    double lat;
    @ApiModelProperty(required = true, value = "경도", example = "127.3007637")
    double lon;
}
