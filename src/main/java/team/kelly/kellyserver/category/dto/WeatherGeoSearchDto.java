package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeatherGeoSearchDto {
    @ApiModelProperty(required = true, value = "위도", example = "37.6576769")
    double lat;
    @ApiModelProperty(required = true, value = "경도", example = "127.3007637")
    double lon;
}
