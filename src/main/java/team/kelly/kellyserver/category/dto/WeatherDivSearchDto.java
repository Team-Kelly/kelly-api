package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherDivSearchDto {
    @ApiModelProperty(required = true, value = "행정구역명", example = "경기도 남양주시")
    String adminDivision;
}
