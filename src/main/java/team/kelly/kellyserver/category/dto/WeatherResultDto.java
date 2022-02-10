package team.kelly.kellyserver.category.dto;

import com.google.api.client.util.Lists;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherResultDto {

    @ApiModelProperty(required = true, value = "강수확률")
    List<Integer> rainProb = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "강수상태코드")
    List<Integer> rainStatusCode = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "강수상태설명")
    List<String> rainStatusDetail = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "하늘상태코드")
    List<Integer> skyStatusCode = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "하늘상태설명")
    List<String> skyStatusDetail = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "날씨상태코드")
    List<Integer> weatherStatusCode = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "날씨상태설명")
    List<String> weatherStatusDetail = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "온도")
    List<Integer> temp = Lists.newArrayList();

}
