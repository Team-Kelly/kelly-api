package team.kelly.kellyserver.mock;

import team.kelly.kellyserver.mock.dto.MockWeatherDto;

import java.util.Arrays;
import java.util.List;

public class WeatherDataGenerator {

    public static List<MockWeatherDto> getDayWeatherList() {
        MockWeatherDto mockWeatherDto1 = new MockWeatherDto("01", 25, "맑음");
        MockWeatherDto mockWeatherDto2 = new MockWeatherDto("02", -3, "눈");
        MockWeatherDto mockWeatherDto3 = new MockWeatherDto("03", 23, "비");
        MockWeatherDto mockWeatherDto4 = new MockWeatherDto("04", -22, "흐림");
        MockWeatherDto mockWeatherDto5 = new MockWeatherDto("01", 21, "맑음");
        MockWeatherDto mockWeatherDto6 = new MockWeatherDto("01", 25, "맑음");
        MockWeatherDto mockWeatherDto7 = new MockWeatherDto("01", 27, "맑음");
        MockWeatherDto mockWeatherDto8 = new MockWeatherDto("01", 28, "맑음");
        MockWeatherDto mockWeatherDto9 = new MockWeatherDto("01", -6, "맑음");
        MockWeatherDto mockWeatherDto10 = new MockWeatherDto("04", 27, "흐림");
        MockWeatherDto mockWeatherDto11 = new MockWeatherDto("04", 24, "흐림");
        MockWeatherDto mockWeatherDto12 = new MockWeatherDto("04", 23, "흐림");
        MockWeatherDto mockWeatherDto13 = new MockWeatherDto("01", 22, "맑음");
        MockWeatherDto mockWeatherDto14 = new MockWeatherDto("01", 21, "맑음");
        MockWeatherDto mockWeatherDto15 = new MockWeatherDto("01", 10, "맑음");
        MockWeatherDto mockWeatherDto16 = new MockWeatherDto("01", -2, "맑음");
        MockWeatherDto mockWeatherDto17 = new MockWeatherDto("01", 34, "맑음");
        MockWeatherDto mockWeatherDto18 = new MockWeatherDto("01", -12, "맑음");
        MockWeatherDto mockWeatherDto19 = new MockWeatherDto("02", 23, "눈");
        MockWeatherDto mockWeatherDto20 = new MockWeatherDto("03", 24, "비");
        MockWeatherDto mockWeatherDto21 = new MockWeatherDto("04", 26, "흐림");
        MockWeatherDto mockWeatherDto22 = new MockWeatherDto("01", 60, "맑음");
        MockWeatherDto mockWeatherDto23 = new MockWeatherDto("01", 30, "맑음");
        MockWeatherDto mockWeatherDto24 = new MockWeatherDto("01", 40, "맑음");

        List<MockWeatherDto> mockWeatherDtoList = Arrays.asList(
                mockWeatherDto1, mockWeatherDto2, mockWeatherDto3, mockWeatherDto4, mockWeatherDto5, mockWeatherDto6,
                mockWeatherDto7, mockWeatherDto8, mockWeatherDto9, mockWeatherDto10, mockWeatherDto11, mockWeatherDto12,
                mockWeatherDto13, mockWeatherDto14, mockWeatherDto15, mockWeatherDto16, mockWeatherDto17, mockWeatherDto18,
                mockWeatherDto19, mockWeatherDto20, mockWeatherDto21, mockWeatherDto22, mockWeatherDto23, mockWeatherDto24);
        return mockWeatherDtoList;
    }
}
