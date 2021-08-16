package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SubwaySearchInfoDto {

    @ApiModelProperty(example = "서울")
    private String statnNm;
    @ApiModelProperty(example = "하행")
    private String updnLine;
    @ApiModelProperty(example = "\"1001\"")
    private String subwayId;
    @ApiModelProperty(example = "\"1004000426\"")
    private String statnId;


}