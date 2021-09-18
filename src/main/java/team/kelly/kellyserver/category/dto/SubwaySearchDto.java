package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubwaySearchDto {

    @ApiModelProperty(example = "서울")
    private String statnNm;
    @ApiModelProperty(example = "상행")
    private String updnLine;
    @ApiModelProperty(example = "\"1001\"")
    private String subwayId;


}