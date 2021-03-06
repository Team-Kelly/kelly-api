package team.kelly.kellyserver.category.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.WeatherResultDto;
import team.kelly.kellyserver.category.dto.WeatherSearchDto;
import team.kelly.kellyserver.category.resource.WeatherPhrase;
import team.kelly.kellyserver.common.GlobalValue;
import team.kelly.kellyserver.common.utility.ApiUtility;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WeatherService {

    static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/onecall?lat=%f&lon=%f&exclude=daily,minutely,current,alerts&units=metric&appid=%s";
    static String[] WEATHER_STATUS = {"맑음", "구름조금", "구름많음", "비", "천둥", "눈", "흐림"};
    static String[] WEATHER_STATUS_PRIORITY = {"천둥", "눈", "비", "흐림", "구름많음", "구름적음", "맑음"};
    private final GlobalValue globalValue;
    @Value("${apikey.openweather}")
    String openWeatherApiKey;

    public Map<String, String> getWeatherPhrase(WeatherSearchDto infoVO) throws IOException {

        Map<String, String> result = new HashMap<>();

        if (globalValue.isWeatherCallExceed()) {
            result.put("phrase", "오늘도 좋은 하루 되세요 :)");
            return result;
        }

        List<WeatherResultDto> oneDayWeather = getOneDayWeatherData(infoVO);

        int curStatus = getWeatherStatusByPriority(oneDayWeather.subList(0, 6).stream().map(x -> x.getWeatherStatusDetail()).collect(Collectors.toList()));
        int futureStatus = getWeatherStatusByPriority(oneDayWeather.subList(6, 12).stream().map(x -> x.getWeatherStatusDetail()).collect(Collectors.toList()));

        result.put("phrase", WeatherPhrase.phrase[curStatus][futureStatus]);

        return result;
    }

    private int getWeatherStatusByPriority(List<String> weatherStatusList) {

        List<String> list = Arrays.asList(WEATHER_STATUS);

        for (String status : WEATHER_STATUS_PRIORITY) {
            if (weatherStatusList.contains(status)) {
                return list.indexOf(status);
            }
        }

        return 0;

    }

    public List<WeatherResultDto> getOneDayWeatherData(WeatherSearchDto infoVO) throws IOException {

        if (globalValue.isWeatherCallExceed()) {
            return new ArrayList<>();
        }

        String url = String.format(WEATHER_URL, infoVO.getLat(), infoVO.getLon(), openWeatherApiKey);

        globalValue.increaseWeatherCallCount();

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
