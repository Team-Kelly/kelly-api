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
    @ApiModelProperty(required = true, value = "강수상태")
    List<Integer> rainStatus = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "날씨상태")
    List<Integer> skyStatus = Lists.newArrayList();
    @ApiModelProperty(required = true, value = "온도")
    List<Integer> temp = Lists.newArrayList();

}
