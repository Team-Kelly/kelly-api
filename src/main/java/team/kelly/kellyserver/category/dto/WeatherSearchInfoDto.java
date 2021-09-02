package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeatherSearchInfoDto {
    @ApiModelProperty(example = "37.6576769")
    double lat;
    @ApiModelProperty(example = "127.3007637")
    double lon;
}
