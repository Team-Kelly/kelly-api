package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.WeatherResultInfoDto;
import team.kelly.kellyserver.category.dto.WeatherSearchInfoDto;
import team.kelly.kellyserver.common.ApiUtility;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static team.kelly.kellyserver.common.CustomJSONUtility.sortJsonArray;

@Slf4j
@Service
public class WeatherService {

    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String weatherGetInfoUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst?serviceKey=";

    static String[] SKY_STATUS = {"없음", "맑음", "없음", "구름많음", "흐림"};
    static String[] PTY_STATUS = {"없음", "비", "비/눈", "눈", "소나기", "빗방울", "빗방울눈날림", "눈날림"};


    public WeatherResultInfoDto getCurrentWeatherData(WeatherSearchInfoDto infoVO) throws IOException {

        WeatherResultInfoDto result = new WeatherResultInfoDto();

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (cal.get(Calendar.MINUTE) <= 30) {
            cal.add(Calendar.HOUR, -1);
        }

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");


        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String base_time = lastVersion.substring(lastVersion.length() - 4, lastVersion.length() - 2) + "30";


        String jsonStr = ApiUtility.callApi(weatherGetInfoUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=100&dataType=XML&base_date="
                + base_date + "&base_time=" + base_time + "&nx=" + infoVO.getNx() + "&ny=" + infoVO.getNy());

        JSONObject jsonObject = new JSONObject(jsonStr);

        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").
                getJSONObject("items").getJSONArray("item");
        jsonArray = sortJsonArray(jsonArray);


        for (int i = 0; i < 10; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            if (obj.getString("category").equals("T1H"))
                result.setTemp(obj.getInt("fcstValue") + "°C");
            else if (obj.getString("category").equals("REH"))
                result.setHumi(obj.getInt("fcstValue") + "%");
            else if (obj.getString("category").equals("SKY"))
                result.setSky(SKY_STATUS[obj.getInt("fcstValue")]);
            else if (obj.getString("category").equals("PTY"))
                result.setRain(PTY_STATUS[obj.getInt("fcstValue")]);
        }

        return result;


    }


}
