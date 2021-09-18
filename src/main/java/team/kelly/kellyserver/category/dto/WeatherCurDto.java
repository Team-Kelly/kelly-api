package team.kelly.kellyserver.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherCurDto {
    int minTemp;
    int maxTemp;
    int rainProb;
}
