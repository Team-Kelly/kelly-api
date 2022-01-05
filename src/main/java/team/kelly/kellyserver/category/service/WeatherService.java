package team.kelly.kellyserver.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.*;
import team.kelly.kellyserver.common.utility.ApiUtility;
import team.kelly.kellyserver.common.utility.TransLocalPoint;
import team.kelly.kellyserver.db.entity.RegionCode;
import team.kelly.kellyserver.db.repository.RegionCodeRepository;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static team.kelly.kellyserver.common.utility.CustomJSONUtility.sortJsonArray;

@Slf4j
@Service
@RequiredArgsConstructor
public class WeatherService {

    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String VilageUltraFcstWeatherUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtFcst?serviceKey=";
    static final String VilageWeatherUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?serviceKey=";
    static final String MidTaWeatherUrlPrefix = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidTa?serviceKey=";
    static final String MidLandWeatherUrlPrefix = "http://apis.data.go.kr/1360000/MidFcstInfoService/getMidLandFcst?serviceKey=";
    private final RegionCodeRepository regionCodeRepository;

    static String[] SKY_STATUS = {"없음", "맑음", "없음", "구름많음", "흐림"};
    static String[] PTY_STATUS = {"없음", "비", "비/눈", "눈", "소나기", "빗방울", "빗방울눈날림", "눈날림"};

    /*
    현재 날씨 정보를 가져옴
     */
    public WeatherCurResultDto getCurrentWeatherData(WeatherGeoSearchDto infoVO) throws IOException {

        GridValueDto gridValueDto = TransLocalPoint.getGridxy(infoVO.getLat(), infoVO.getLon());

        WeatherCurDto weatherCurDto = getVilageFcstWeatherData(gridValueDto);
        WeatherUltraCurDto weatherUltraCurDto = getVilageUltraFcstWeatherData(gridValueDto);
        WeatherCurResultDto weatherCurResultDto = WeatherCurResultDto.combineToDto(weatherCurDto, weatherUltraCurDto);
        return weatherCurResultDto;

    }

    /*
    일주일 날씨 정보를 가져옴
     */
    public WeatherMidResultDto getWeekWeatherData(WeatherDivSearchDto infoVO, WeatherGeoSearchDto weatherGeoSearchDto) throws IOException {

        GridValueDto gridValueDto = TransLocalPoint.getGridxy(weatherGeoSearchDto.getLat(), weatherGeoSearchDto.getLon());

        AdminDivisionDto adminDivisionDto = new AdminDivisionDto("경기도", "남양주");
        RegionCode regionCode = regionCodeRepository.findByRegionAndCity(adminDivisionDto.getRegion(), adminDivisionDto.getCity());
        WeatherMidResultDto weatherMidResultDto = new WeatherMidResultDto();

        getWeekTaWeatherData(regionCode.getCode(), weatherMidResultDto);
        getWeekLandWeatherData(getLandWeatherCode(regionCode.getCode()), weatherMidResultDto);
        getShortWeekVilageFcstWeatherData(gridValueDto, weatherMidResultDto);

        return weatherMidResultDto;
    }

    /*
    일주일 날씨 정보를 가져옴
     */
    public String getLandWeatherCode(String code) throws IOException {

        char mid = code.charAt(2);
        if (mid == 'B' || mid == 'G') {
            return code.substring(0, 3) + "00000";
        } else {
            return code.substring(0, 4) + "0000";
        }
    }

    /*
    현재 날씨의 기온, 강수형태, 날씨형태를 가져옴
     */
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

        JSONArray jsonArray = callVillageWeatherApi(gridValueDto, base_date, base_time, VilageUltraFcstWeatherUrlPrefix);

        boolean t1hCheck = false, skyCheck = false, rainCheck = false;

        for (int i = 0; i < 10; i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            if (t1hCheck && skyCheck && rainCheck) {
                break;
            }

            if (!t1hCheck && obj.getString("category").equals("T1H")) {
                result.setCurTemp(obj.getInt("fcstValue"));
                t1hCheck = true;
            } else if (!skyCheck && obj.getString("category").equals("SKY")) {
                result.setSkyStatus(obj.getInt("fcstValue"));
                skyCheck = true;
            } else if (!rainCheck && obj.getString("category").equals("PTY")) {
                result.setRainStatus(obj.getInt("fcstValue"));
                rainCheck = true;
            }
        }

        return result;

    }

    /*
    현재 날씨의 강수확률, 오늘 날씨의 최고기온/최저기온을 가져옴
     */
    public WeatherCurDto getVilageFcstWeatherData(GridValueDto gridValueDto) throws IOException {

        WeatherCurDto result = new WeatherCurDto();
        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        String curVersion = df.format(cal.getTime());
        String cur_date = curVersion.substring(0, curVersion.length() - 4);

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

        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String base_time_pop = String.format("%02d", dataGenHour) + "00";
        String base_time_temp = "0200";

        JSONArray jsonArrayForProb = callVillageWeatherApi(gridValueDto, base_date, base_time_pop, VilageWeatherUrlPrefix);

        for (int i = 0; i < jsonArrayForProb.length(); i++) {
            JSONObject obj = jsonArrayForProb.getJSONObject(i);

            if (obj.getString("category").equals("POP")) {
                result.setRainProb(obj.getInt("fcstValue"));
                break;
            }
        }

        JSONArray jsonArrayForTemp = callVillageWeatherApi(gridValueDto, base_date, base_time_temp, VilageWeatherUrlPrefix);

        boolean tmnCheck = false, tmxCheck = false;

        for (int i = 0; i < jsonArrayForTemp.length(); i++) {
            JSONObject obj = jsonArrayForTemp.getJSONObject(i);

            if (tmnCheck && tmxCheck) {
                break;
            }
            if (!tmnCheck && obj.getString("category").equals("TMN") && obj.get("fcstDate").toString().equals(cur_date)) {
                result.setMinTemp((int) obj.get("fcstValue"));
                tmnCheck = true;
            } else if (!tmxCheck && obj.getString("category").equals("TMX") && obj.get("fcstDate").toString().equals(cur_date)) {
                result.setMaxTemp((int) obj.get("fcstValue"));
                tmxCheck = true;
            }
        }

        return result;

    }

    /*
    1~2일까지의 강수확률 평균, 날씨의 최고기온/최저기온을 가져옴
     */
    public WeatherMidResultDto getShortWeekVilageFcstWeatherData(GridValueDto gridValueDto, WeatherMidResultDto weatherMidResultDto) throws IOException {

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());

        Calendar d1cal = Calendar.getInstance();
        d1cal.setTime(new Date());
        Calendar d2cal = Calendar.getInstance();
        d2cal.setTime(new Date());

        String curVersion = df.format(cal.getTime());

        d1cal.add(Calendar.DATE, +1);
        d2cal.add(Calendar.DATE, +2);

        if (cal.get(Calendar.MINUTE) <= 10) {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }
        if (cal.get(Calendar.HOUR_OF_DAY) == 0) {
            cal.add(Calendar.HOUR_OF_DAY, -1);
        }
        if (cal.get(Calendar.HOUR_OF_DAY) == 1) {
            cal.add(Calendar.HOUR_OF_DAY, -2);
        }

        String lastVersion = df.format(cal.getTime());
        String base_date = lastVersion.substring(0, lastVersion.length() - 4);
        String d1_date = df.format(d1cal.getTime()).substring(0, lastVersion.length() - 4);
        String d2_date = df.format(d2cal.getTime()).substring(0, lastVersion.length() - 4);
        String base_time_temp = "0200";

        JSONArray jsonArrayForTemp = callVillageWeatherApi(gridValueDto, base_date, base_time_temp, VilageWeatherUrlPrefix);

        boolean tmnCheck = false, tmxCheck = false;

        for (int i = 0; i < jsonArrayForTemp.length(); i++) {
            JSONObject obj = jsonArrayForTemp.getJSONObject(i);

            if (tmnCheck && tmxCheck) {
                break;
            }
            if (obj.getString("category").equals("TMN")) {
                if (obj.get("fcstDate").toString().equals(d1_date)) {
                    weatherMidResultDto.getMinTemp()[0] = (int) obj.get("fcstValue");
                } else if (obj.get("fcstDate").toString().equals(d2_date)) {
                    weatherMidResultDto.getMinTemp()[1] = (int) obj.get("fcstValue");
                }
            } else if (obj.getString("category").equals("TMX")) {
                if (obj.get("fcstDate").toString().equals(d1_date)) {
                    weatherMidResultDto.getMaxTemp()[0] = (int) obj.get("fcstValue");
                } else if (obj.get("fcstDate").toString().equals(d2_date)) {
                    weatherMidResultDto.getMaxTemp()[1] = (int) obj.get("fcstValue");
                }
            } else if (obj.getString("category").equals("POP")) {
                if (obj.get("fcstDate").toString().equals(d1_date)) {
                    weatherMidResultDto.getRainProb()[0] += (int) obj.get("fcstValue");
                } else if (obj.get("fcstDate").toString().equals(d2_date)) {
                    weatherMidResultDto.getRainProb()[1] += (int) obj.get("fcstValue");
                }
            } else if (obj.getString("category").equals("SKY")) {
                if (obj.get("fcstDate").toString().equals(d1_date)) {
                    weatherMidResultDto.getSkyStatus()[0] = SKY_STATUS[obj.getInt("fcstValue")];
                } else if (obj.get("fcstDate").toString().equals(d2_date)) {
                    weatherMidResultDto.getSkyStatus()[1] = SKY_STATUS[obj.getInt("fcstValue")];
                }
            }
            weatherMidResultDto.getRainProb()[0] = weatherMidResultDto.getRainProb()[0] / 24;
            weatherMidResultDto.getRainProb()[1] = weatherMidResultDto.getRainProb()[1] / 24;

        }

        return weatherMidResultDto;

    }

    /*
    3~7일 날씨의 최고기온/최저기온을 가져옴
     */
    public WeatherMidResultDto getWeekTaWeatherData(String code, WeatherMidResultDto weatherMidResultDto) throws IOException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        String base_time = "";
        //최근 시간대의 정보를 얻어옴 (최대 api 제공 오차 15분)

        if (curHour > 6 && curHour <= 18) {
            base_time = "0600";
        } else if (curHour <= 6) {
            base_time = "1800";
            cal.add(Calendar.HOUR_OF_DAY, -12);
        } else if (curHour > 18) {
            base_time = "1800";
        }

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        String lastVersion = df.format(cal.getTime());
        //기준 날짜 가져옴
        String base_tm = lastVersion.substring(0, lastVersion.length() - 4) + base_time;
        //기준 시간 가져옴, api 제공 base time은 매 시간 30분임

        JSONObject obj = callMidWeatherApi(MidTaWeatherUrlPrefix, code, base_tm);

        for (int i = 3; i < 8; i++) {
            weatherMidResultDto.getMinTemp()[i - 1] = obj.getInt("taMin" + i);
            weatherMidResultDto.getMaxTemp()[i - 1] = obj.getInt("taMax" + i);
        }

        return weatherMidResultDto;

    }

    /*
    3~7일 날씨의 강수확률/날씨예보를 가져옴
     */
    public WeatherMidResultDto getWeekLandWeatherData(String code, WeatherMidResultDto weatherMidResultDto) throws IOException {

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int curHour = cal.get(Calendar.HOUR_OF_DAY);
        String base_time = "";
        //최근 시간대의 정보를 얻어옴 (최대 api 제공 오차 15분)

        if (curHour > 6 && curHour <= 18) {
            base_time = "0600";
        } else if (curHour <= 6) {
            base_time = "1800";
            cal.add(Calendar.HOUR_OF_DAY, -12);
        } else if (curHour > 18) {
            base_time = "1800";
        }

        DateFormat df = new SimpleDateFormat("yyyyMMddHHMM");

        String lastVersion = df.format(cal.getTime());
        //기준 날짜 가져옴
        String base_tm = lastVersion.substring(0, lastVersion.length() - 4) + base_time;
        //기준 시간 가져옴, api 제공 base time은 매 시간 30분임

        JSONObject obj = callMidWeatherApi(MidLandWeatherUrlPrefix, code, base_tm);

        for (int i = 3; i < 8; i++) {
            weatherMidResultDto.getRainProb()[i - 1] = (obj.getInt("rnSt" + i + "Am") + obj.getInt("rnSt" + i + "Pm")) / 2;
            weatherMidResultDto.getSkyStatus()[i - 1] = obj.getString("wf" + i + "Am");
        }

        return weatherMidResultDto;

    }

    /*
    기상청의 단기 예보 api를 호출
     */
    private JSONArray callVillageWeatherApi(GridValueDto gridValueDto, String base_date, String base_time, String villageWeatherUrlPrefix) throws IOException {
        String jsonStr = ApiUtility.callApi(villageWeatherUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=1000&dataType=XML&base_date="
                + base_date + "&base_time=" + base_time + "&nx=" + gridValueDto.getGridx() + "&ny=" + gridValueDto.getGridy());

        JSONObject jsonObject = new JSONObject(jsonStr);

        log.info(villageWeatherUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=1000&dataType=XML&base_date="
                + base_date + "&base_time=" + base_time + "&nx=" + gridValueDto.getGridx() + "&ny=" + gridValueDto.getGridy());

        JSONArray jsonArray = jsonObject.getJSONObject("response").getJSONObject("body").
                getJSONObject("items").getJSONArray("item");

        return sortJsonArray(jsonArray);
    }

    /*
    기상청의 중기 예보 api를 호출
     */
    private JSONObject callMidWeatherApi(String midWeatherUrlPrefix, String regId, String base_tm) throws IOException {
        String jsonStr = ApiUtility.callApi(midWeatherUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=200&dataType=XML&regId="
                + regId + "&tmFc=" + base_tm);

        log.info(midWeatherUrlPrefix + govOpenApiKey + "&pageNo=1&numOfRows=200&dataType=XML&regId="
                + regId + "&tmFc=" + base_tm);

        JSONObject jsonObject = new JSONObject(jsonStr);

        jsonObject = jsonObject.getJSONObject("response").getJSONObject("body").
                getJSONObject("items").getJSONObject("item");

        return jsonObject;
    }


}
