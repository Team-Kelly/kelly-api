package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.*;
import team.kelly.kellyserver.common.utility.ApiUtility;
import team.kelly.kellyserver.common.utility.TransLocalPoint;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static team.kelly.kellyserver.common.utility.CustomJSONUtility.sortJsonArray;

@Slf4j
@Service
public class WeatherService {

    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String VilageUltraFcstWeatherUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=";
    static final String VilageWeatherUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";

    static String[] SKY_STATUS = {"없음", "맑음", "없음", "구름많음", "흐림"};
    static String[] PTY_STATUS = {"없음", "비", "비/눈", "눈", "소나기", "빗방울", "빗방울눈날림", "눈날림"};

    public WeatherResultDto getCurrentWeatherData(WeatherSearchDto infoVO) throws IOException {

        GridValueDto gridValueDto = TransLocalPoint.getGridxy(infoVO.getLat(), infoVO.getLon());

        WeatherCurDto weatherCurDto = getVilageFcstWeatherData(gridValueDto);
        WeatherUltraCurDto weatherUltraCurDto = getVilageUltraFcstWeatherData(gridValueDto);
        WeatherResultDto weatherResultDto = WeatherResultDto.combineToDto(weatherCurDto, weatherUltraCurDto);
        return weatherResultDto;

    }

    public WeatherUltraCurDto getVilageUltraFcstWeatherData(GridValueDto gridValueDto) throws IOException {

        WeatherUltraCurDto result = new WeatherUltraCurDto();


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        //최근 시간대의 정보를 얻어옴 (최대 api 제공 오차 15분)
        cal.add(Calendar.HOUR_OF_DAY, -1);

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        String lastVersion = df.format(cal.getTime());
        //기준 날짜 가져옴
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        //기준 시간 가져옴, api 제공 base time은 매 시간 30분임
        String base_time = lastVersion.substring(lastVersion.length() - 4, lastVersion.length() - 2) + "30";

        JSONArray jsonArray = callWeatherApi(gridValueDto, base_date, base_time, VilageUltraFcstWeatherUrlPrefix);

        for (int i = 0; i < 10; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            if (obj.getString("category").equals("T1H"))
                result.setCurTemp(obj.getInt("fcstValue"));
            else if (obj.getString("category").equals("SKY"))
                result.setSkyStatus(obj.getInt("fcstValue"));
            else if (obj.getString("category").equals("PTY"))
                result.setRainStatus(obj.getInt("fcstValue"));
        }

        return result;

    }

    public WeatherCurDto getVilageFcstWeatherData(GridValueDto gridValueDto) throws IOException {

        WeatherCurDto result = new WeatherCurDto();


        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        if (cal.get(Calendar.MINUTE) <= 10) {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }
        if (cal.get(Calendar.HOUR_OF_DAY) == 0) {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }
        if (cal.get(Calendar.HOUR_OF_DAY) == 1) {
            cal.add(Calendar.HOUR_OF_DAY, -2);
        }

        //제공 API 시간은 2시부터 3시간 간격
        int dataGenHour = ((int) (cal.get(Calendar.HOUR_OF_DAY) + 1) / 3) * 3 - 1;

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String base_time_pop = String.format("%02d", dataGenHour) + "00";
        String base_time_temp = "0200";

        JSONArray jsonArrayForProb = callWeatherApi(gridValueDto, base_date, base_time_pop, VilageWeatherUrlPrefix);

        for (int i = 0; i < 11; i++) {
            JSONObject obj = jsonArrayForProb.getJSONObject(i);

            if (obj.getString("category").equals("POP"))
                result.setRainProb(obj.getInt("fcstValue"));
        }

        JSONArray jsonArrayForTemp = callWeatherApi(gridValueDto, base_date, base_time_temp, VilageWeatherUrlPrefix);

        for (int i = 0; i < 200; i++) {
            JSONObject obj = jsonArrayForTemp.getJSONObject(i);

            if (obj.getString("category").equals("TMN")) {
                result.setMinTemp((int) obj.get("fcstValue"));
            } else if (obj.getString("category").equals("TMX"))
                result.setMaxTemp((int) obj.get("fcstValue"));
        }

        return result;

    }

    private JSONArray callWeatherApi(GridValueDto gridValueDto, String base_date, String base_time, String vilageWeatherUrlPrefix) throws IOException {
        String jsonStr = ApiUtility.callApi(vilageWeatherUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=200&dataType=XML&base_date="
                + base_date + "&base_time=" + base_time + "&nx=" + gridValueDto.getGridx() + "&ny=" + gridValueDto.getGridy());

        JSONObject jsonObject = new JSONObject(jsonStr);

        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").
                getJSONObject("items").getJSONArray("item");

        return sortJsonArray(jsonArray);
    }


}
