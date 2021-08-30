package team.kelly.kellyserver.category.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherResultInfoDto {
    String rain;
    String sky;
    String temp;
    String humi;
}
