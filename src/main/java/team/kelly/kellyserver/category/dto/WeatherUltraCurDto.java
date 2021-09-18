package team.kelly.kellyserver.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherUltraCurDto {
    int rainStatus;
    int skyStatus;
    int curTemp;
}
