package team.kelly.kellyserver.mock.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MockWeatherDto {
    @ApiModelProperty(required = true, value = "강수확률")
    int rainProb;
    @ApiModelProperty(required = true, value = "날씨상태코드")
    int weatherStatusCode;
    @ApiModelProperty(required = true, value = "날씨상태설명")
    String weatherStatusDetail;
    @ApiModelProperty(required = true, value = "온도")
    int temp;
}
