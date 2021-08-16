package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeatherSearchInfoDto {
    @ApiModelProperty(example = "\"65\"")
    String nx;
    @ApiModelProperty(example = "\"129\"")
    String ny;
}
