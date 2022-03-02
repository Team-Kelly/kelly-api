package team.kelly.kellyserver.category.service;

import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.WeatherResultDto;
import team.kelly.kellyserver.category.dto.WeatherSearchDto;
import team.kelly.kellyserver.common.utility.ApiUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class WeatherService {

    @Value("${apikey.openweather}")
    String openWeatherApiKey;
    static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%f&lon=%f&exclude=daily,minutely,current,alerts&units=metric&appid=%s";

    static String[] WEATHER_STATUS = {"맑음", "구름조금", "구름많음", "비", "천둥", "눈", "흐림"};

    public List<WeatherResultDto> getOneDayWeatherData(WeatherSearchDto infoVO) throws IOException {

        String url = String.format(WEATHER_URL, infoVO.getLat(), infoVO.getLon(), openWeatherApiKey);
        String jsonStr = ApiUtility.callApi(url, "json");
        JSONObject jsonObject = new JSONObject(jsonStr);
        JSONArray hourlyWeatherArray = jsonObject.getJSONArray("hourly");

        List<WeatherResultDto> result = new ArrayList<>();

        for (int i = 0; i < hourlyWeatherArray.length(); i++) {
            WeatherResultDto dto = new WeatherResultDto();
            JSONObject weather = hourlyWeatherArray.getJSONObject(i);
            dto.setTemp((int) Math.round(weather.getDouble("temp")));
            dto.setRainProb((int) Math.round(weather.getDouble("pop") * 100));
            int weatherStatusIndex = getWeatherStatus(weather.getJSONArray("weather").getJSONObject(0).get("description").toString());
            dto.setWeatherStatusCode(weatherStatusIndex);
            dto.setWeatherStatusDetail(WEATHER_STATUS[weatherStatusIndex]);

            result.add(dto);

        }
        return result;
    }

    /*
    날씨 상태를 결정한다
     */
    public int getWeatherStatus(String weatherDescription) {
        if (weatherDescription.equals("clear sky")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "맑음");
        } else if (weatherDescription.equals("few clouds")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "구름조금");
        } else if (weatherDescription.equals("scattered clouds")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "구름많음");
        } else if (weatherDescription.equals("broken clouds")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "구름많음");
        } else if (weatherDescription.equals("shower rain")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "비");
        } else if (weatherDescription.equals("rain")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "비");
        } else if (weatherDescription.equals("thunderstorm")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "천둥");
        } else if (weatherDescription.equals("snow")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "눈");
        } else {
            return ArrayUtils.indexOf(WEATHER_STATUS, "흐림");
        }
    }

}
