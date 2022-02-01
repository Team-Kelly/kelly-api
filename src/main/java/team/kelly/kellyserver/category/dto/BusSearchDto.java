package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusSearchDto {
    @ApiModelProperty(example = "\"122900003\"")
    private String busId;
    @ApiModelProperty(example = "\"23489\"")
    private String stationId;
    @ApiModelProperty(example = "\"1000\"")
    private String cityCode;
}
