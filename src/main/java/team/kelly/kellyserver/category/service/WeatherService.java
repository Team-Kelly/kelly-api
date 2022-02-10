package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.GridValueDto;
import team.kelly.kellyserver.category.dto.WeatherDetailTempDto;
import team.kelly.kellyserver.category.dto.WeatherResultDto;
import team.kelly.kellyserver.category.dto.WeatherSearchDto;
import team.kelly.kellyserver.common.utility.ApiUtility;
import team.kelly.kellyserver.common.utility.TransLocalPoint;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.min;
import static team.kelly.kellyserver.common.utility.CustomJSONUtility.sortJsonArray;

@Slf4j
@Service
public class WeatherService {

    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String VILAGE_WEATHER_URL_PREFIX = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";

    static String[] SKY_STATUS = {"없음", "맑음", "없음", "구름많음", "흐림"};
    static String[] RAIN_STATUS = {"없음", "비", "비/눈", "눈", "소나기"};
    static String[] WEATHER_STATUS = {"맑음", "구름많음", "흐림", "비", "비/눈", "눈", "소나기"};

    public WeatherResultDto getOneDayWeatherData(WeatherSearchDto infoVO) throws IOException {

        GridValueDto gridValueDto = TransLocalPoint.getGridxy(infoVO.getLat(), infoVO.getLon());

        WeatherResultDto resultDto = getVilageFcstWeatherData(gridValueDto);

        for (int i = 0; i < min(resultDto.getSkyStatusCode().size(), resultDto.getRainStatusCode().size()); i++) {

            int weatherStatusCode = getWeatherStatusByPriority(resultDto.getSkyStatusCode().get(i), resultDto.getRainStatusCode().get(i));
            resultDto.getWeatherStatusCode().add(weatherStatusCode);
            resultDto.getWeatherStatusDetail().add(WEATHER_STATUS[weatherStatusCode]);

        }

        return resultDto;

    }

    public Calendar getLastObserveVersionDay() {

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

        return cal;
    }

    public WeatherResultDto getVilageFcstWeatherData(GridValueDto gridValueDto) throws IOException {

        WeatherResultDto result = new WeatherResultDto();

        Calendar cal = getLastObserveVersionDay();

        //제공 API 시간은 2시부터 3시간 간격
        int dataGenHour = ((int) (cal.get(Calendar.HOUR_OF_DAY) + 1) / 3) * 3 - 1;

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String base_time_pop = String.format("%02d", dataGenHour) + "00";

        for (int i = 1; i < 3; i++) {

            JSONArray jsonArrayForProb = callWeatherApi(gridValueDto, base_date, base_time_pop, VILAGE_WEATHER_URL_PREFIX, i);

            log.info(String.valueOf(jsonArrayForProb));

            for (int k = 0; k < 200; k++) {
                JSONObject obj = jsonArrayForProb.getJSONObject(k);

                if (obj.getString("category").equals("POP")) {
                    result.getRainProb().add(obj.getInt("fcstValue"));
                }
                if (obj.getString("category").equals("PTY")) {
                    result.getRainStatusCode().add(obj.getInt("fcstValue"));
                    result.getRainStatusDetail().add(RAIN_STATUS[obj.getInt("fcstValue")]);
                }
                if (obj.getString("category").equals("SKY")) {
                    result.getSkyStatusCode().add(obj.getInt("fcstValue"));
                    result.getSkyStatusDetail().add(SKY_STATUS[obj.getInt("fcstValue")]);
                }
                if (obj.getString("category").equals("TMP")) {
                    result.getTemp().add(obj.getInt("fcstValue"));
                }

            }
        }

        return result;

    }

    public WeatherDetailTempDto getDetailTempWeatherData(GridValueDto gridValueDto) throws IOException {

        WeatherDetailTempDto result = new WeatherDetailTempDto();

        Calendar cal = getLastObserveVersionDay();

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");
        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String base_time_temp = "0200";

        JSONArray jsonArrayForTemp = callWeatherApi(gridValueDto, base_date, base_time_temp, VILAGE_WEATHER_URL_PREFIX, 1);

        for (int i = 0; i < 200; i++) {
            JSONObject obj = jsonArrayForTemp.getJSONObject(i);

            if (obj.getString("category").equals("TMN")) {
                result.setMinTemp((int) obj.get("fcstValue"));
            } else if (obj.getString("category").equals("TMX"))
                result.setMaxTemp((int) obj.get("fcstValue"));
        }

        return result;

    }

    private JSONArray callWeatherApi(GridValueDto gridValueDto, String base_date, String base_time, String vilageWeatherUrlPrefix, int pageNo) throws IOException {
        String jsonStr = ApiUtility.callApi(vilageWeatherUrlPrefix + govOpenApiKey + "&pageNo=" + pageNo + "&numOfRows=200&dataType=XML&base_date="
                + base_date + "&base_time=" + base_time + "&nx=" + gridValueDto.getGridx() + "&ny=" + gridValueDto.getGridy());

        JSONObject jsonObject = new JSONObject(jsonStr);

        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").
                getJSONObject("items").getJSONArray("item");

        return sortJsonArray(jsonArray);
    }

    /*
    우선순위에 따라 날씨 상태를 결정한다
    우선순위 눈>비/눈>비>소나기>흐림>구름많음>맑음
     */
    public int getWeatherStatusByPriority(int skyStatusCode, int rainStatusCode) {
        if (rainStatusCode == ArrayUtils.indexOf(RAIN_STATUS, "눈")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "눈");
        } else if (rainStatusCode == ArrayUtils.indexOf(RAIN_STATUS, "비/눈")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "비/눈");
        } else if (rainStatusCode == ArrayUtils.indexOf(RAIN_STATUS, "비")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "비");
        } else if (rainStatusCode == ArrayUtils.indexOf(RAIN_STATUS, "소나기")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "소나기");
        } else if (skyStatusCode == ArrayUtils.indexOf(SKY_STATUS, "흐림")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "흐림");
        } else if (skyStatusCode == ArrayUtils.indexOf(SKY_STATUS, "구름많음")) {
            return ArrayUtils.indexOf(WEATHER_STATUS, "구름많음");
        } else {
            return ArrayUtils.indexOf(WEATHER_STATUS, "맑음");
        }
    }

}
