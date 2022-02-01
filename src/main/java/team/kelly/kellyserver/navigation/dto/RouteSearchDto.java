package team.kelly.kellyserver.navigation.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RouteSearchDto {
    @ApiModelProperty(example = "\"127.2864968\"")
    String startX;
    @ApiModelProperty(example = "\"37.6561733\"")
    String startY;
    @ApiModelProperty(example = "\"127.0979449\"")
    String endX;
    @ApiModelProperty(example = "\"37.5132612\"")
    String endY;
    @ApiModelProperty(example = "\"0\"")
    String option; //0: 최단경로, 1: 지하철, 2: 버스
}
