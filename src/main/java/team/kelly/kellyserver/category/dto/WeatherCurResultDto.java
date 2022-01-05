package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherCurResultDto {

    @ApiModelProperty(required = true, value = "최저온도", example = "14")
    int minTemp;
    @ApiModelProperty(required = true, value = "최고온도", example = "28")
    int maxTemp;
    @ApiModelProperty(required = true, value = "강수확률", example = "0")
    int rainProb;
    @ApiModelProperty(required = true, value = "강수상태", example = "1")
    int rainStatus;
    @ApiModelProperty(required = true, value = "날씨상태", example = "0")
    int skyStatus;
    @ApiModelProperty(required = true, value = "현재온도", example = "19")
    int curTemp;

    public static WeatherCurResultDto combineToDto(WeatherCurDto weatherCurDto, WeatherUltraCurDto weatherUltraCurDto) {
        return WeatherCurResultDto.builder()
                .minTemp(weatherCurDto.getMinTemp())
                .maxTemp(weatherCurDto.getMaxTemp())
                .rainProb(weatherCurDto.getRainProb())
                .rainStatus(weatherUltraCurDto.getRainStatus())
                .skyStatus(weatherUltraCurDto.getSkyStatus())
                .curTemp(weatherUltraCurDto.getCurTemp())
                .build();
    }
}
