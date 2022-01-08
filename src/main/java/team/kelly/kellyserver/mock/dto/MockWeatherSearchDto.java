package team.kelly.kellyserver.mock.dto;

import lombok.Data;

@Data
public class MockWeatherSearchDto {
    String curX;
    String curY;
    String address;
    String city;
    String country;
}
