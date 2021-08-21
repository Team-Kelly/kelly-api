package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class BusSearchInfoDto {
    @ApiModelProperty(example = "\"261\"")
    private String busNumber;
    @ApiModelProperty(example = "\"11457\"")
    private String stationNumber;
}
