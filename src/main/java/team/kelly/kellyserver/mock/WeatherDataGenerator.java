package team.kelly.kellyserver.mock;

import team.kelly.kellyserver.mock.dto.MockWeatherDto;

import java.util.Arrays;
import java.util.List;

public class WeatherDataGenerator {

    public static List<MockWeatherDto> getDayWeatherList() {
        MockWeatherDto mockWeatherDto1 = new MockWeatherDto(20, 0, "맑음", 10);
        MockWeatherDto mockWeatherDto2 =  new MockWeatherDto(10, 1, "구름조금", 12);
        MockWeatherDto mockWeatherDto3 = new MockWeatherDto(0, 2, "구름많음", 14);
        MockWeatherDto mockWeatherDto4 = new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto5 =  new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto6 = new MockWeatherDto(10, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto7 =  new MockWeatherDto(0, 0, "맑음", 12);
        MockWeatherDto mockWeatherDto8 =  new MockWeatherDto(20, 2, "구름많음", 13);
        MockWeatherDto mockWeatherDto9 =  new MockWeatherDto(20, 4, "천둥", 14);
        MockWeatherDto mockWeatherDto10 =  new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto11 =  new MockWeatherDto(30, 0, "맑음", 11);
        MockWeatherDto mockWeatherDto12 = new MockWeatherDto(20, 2, "구름많음", 12);
        MockWeatherDto mockWeatherDto13 =  new MockWeatherDto(20, 2, "구름많음", 9);
        MockWeatherDto mockWeatherDto14 = new MockWeatherDto(40, 2, "구름많음", 8);
        MockWeatherDto mockWeatherDto15 =  new MockWeatherDto(20, 3, "비", 12);
        MockWeatherDto mockWeatherDto16 =  new MockWeatherDto(20, 2, "구름많음", 14);
        MockWeatherDto mockWeatherDto17 =  new MockWeatherDto(20, 4, "천둥", 15);
        MockWeatherDto mockWeatherDto18 =  new MockWeatherDto(50, 2, "구름많음", 12);
        MockWeatherDto mockWeatherDto19 =  new MockWeatherDto(60, 5, "눈", 11);
        MockWeatherDto mockWeatherDto20 =  new MockWeatherDto(20, 2, "구름많음", 12);
        MockWeatherDto mockWeatherDto21 =  new MockWeatherDto(20, 2, "구름많음", 13);
        MockWeatherDto mockWeatherDto22 =  new MockWeatherDto(10, 2, "구름많음", 14);
        MockWeatherDto mockWeatherDto23 =  new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto24 =  new MockWeatherDto(30, 2, "구름많음", 12);
        MockWeatherDto mockWeatherDto25 =  new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto26 =  new MockWeatherDto(20, 2, "구름많음", 10);
        MockWeatherDto mockWeatherDto27 =  new MockWeatherDto(50, 2, "구름많음", 15);

        List<MockWeatherDto> mockWeatherDtoList = Arrays.asList(
                mockWeatherDto1, mockWeatherDto2, mockWeatherDto3, mockWeatherDto4, mockWeatherDto5, mockWeatherDto6,
                mockWeatherDto7, mockWeatherDto8, mockWeatherDto9, mockWeatherDto10, mockWeatherDto11, mockWeatherDto12,
                mockWeatherDto13, mockWeatherDto14, mockWeatherDto15, mockWeatherDto16, mockWeatherDto17, mockWeatherDto18,
                mockWeatherDto19, mockWeatherDto20, mockWeatherDto21, mockWeatherDto22, mockWeatherDto23, mockWeatherDto24,
                mockWeatherDto25, mockWeatherDto26, mockWeatherDto27);
        return mockWeatherDtoList;
    }
}
