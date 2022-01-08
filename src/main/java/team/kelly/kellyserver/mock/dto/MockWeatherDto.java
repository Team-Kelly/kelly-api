package team.kelly.kellyserver.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MockWeatherDto {
    String weatherCode;
    int temperature;
    String weatherDetail;
}
