package team.kelly.kellyserver.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherResultDto {

    int minTemp;
    int maxTemp;
    int rainProb;
    int rainStatus;
    int skyStatus;
    int curTemp;

    public static WeatherResultDto combineToDto(WeatherCurDto weatherCurDto, WeatherUltraCurDto weatherUltraCurDto) {
        return WeatherResultDto.builder()
                .minTemp(weatherCurDto.getMinTemp())
                .maxTemp(weatherCurDto.getMaxTemp())
                .rainProb(weatherCurDto.getRainProb())
                .rainStatus(weatherUltraCurDto.getRainStatus())
                .skyStatus(weatherUltraCurDto.getSkyStatus())
                .curTemp(weatherUltraCurDto.getCurTemp())
                .build();
    }
}
