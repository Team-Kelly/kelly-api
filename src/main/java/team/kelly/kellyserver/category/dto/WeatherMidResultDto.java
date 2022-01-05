package team.kelly.kellyserver.category.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WeatherMidResultDto {
    int[] minTemp = {0, 0, 0, 0, 0, 0, 0};
    int[] maxTemp = {0, 0, 0, 0, 0, 0, 0};
    int[] rainProb = {0, 0, 0, 0, 0, 0, 0};
    String[] skyStatus = {"없음", "없음", "없음", "없음", "없음", "없음", "없음"};
}
