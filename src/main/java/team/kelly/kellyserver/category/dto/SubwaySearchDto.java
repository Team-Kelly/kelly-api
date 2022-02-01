package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubwaySearchDto {

    @ApiModelProperty(example = "서울")
    private String stationName;
    @ApiModelProperty(example = "상행")
    private String direction;
    @ApiModelProperty(example = "\"1001\"")
    private String lineId;
    
}