package team.kelly.kellyserver.category.service;

import jep.Interpreter;
import jep.JepException;
import jep.SharedInterpreter;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.BusSearchInfoDto;
import team.kelly.kellyserver.category.dto.CategorySearchInfoDto;
import team.kelly.kellyserver.category.dto.SubwaySearchInfoDto;
import team.kelly.kellyserver.category.dto.WeatherSearchInfoDto;
import team.kelly.kellyserver.common.ApiUtility;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static team.kelly.kellyserver.common.CustomJSONUtility.sortJsonArray;

@Slf4j
@Service
public class CategoryService {

    @Value("${apikey.seouloepnapi}")
    String seoulOpenApiKey;
    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String busUrl = "https://bus.go.kr/xmlRequest/getStationByUid.jsp?strBusNumber=";
    static final String subwayUrlPrefix = "http://swopenapi.seoul.go.kr/api/subway/";
    static final String subwayUrlSuffix = "/xml/realtimeStationArrival/0/1000/";
    static final String weatherGetInfoUrlPrefix = "http://apis.data.go.kr/1360000/VilageFcstInfoService/getUltraSrtFcst?serviceKey=";

    static String[] SKY_STATUS = {"없음", "맑음", "없음", "구름많음", "흐림"};
    static String[] PTY_STATUS = {"없음", "비", "비/눈", "눈", "소나기"};

    public String getBusArriveData(BusSearchInfoDto infoVO) {
        try {
            String jsonStr = ApiUtility.callApi(busUrl + infoVO.getStationNumber());
            JSONObject jsonObject = new JSONObject(jsonStr);
            jsonObject = jsonObject.getJSONObject("Msg");

            log.info(String.valueOf(jsonStr));

            JSONArray jsonArray = new JSONArray();
            if (jsonObject.get("stationList") instanceof JSONArray) {
                jsonArray = jsonObject.getJSONArray("stationList");
            } else {
                jsonArray.put(jsonObject.getJSONObject("stationList"));
            }

            String result = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String rtNm = obj.get("rtNm").toString();

                if (rtNm.equals(infoVO.getBusNumber())) {
                    result += "{ \"arrmsg1\" : \"" + obj.getString("arrmsg1") + "\", " +
                            "\"arrmsg2\" : \"" + obj.getString("arrmsg2") + "\" }";
                    return result;
                }
            }

            return "no such rtNm";

        } catch (Exception e) {
            log.error(e.getMessage() + e.getStackTrace());
            return "api call error";
        }
    }

    public String getSubwayArriveData(SubwaySearchInfoDto infoVO) {
        try {
            String jsonStr = ApiUtility.callApi(subwayUrlPrefix + seoulOpenApiKey + subwayUrlSuffix + URLEncoder.encode(infoVO.getStatnNm(), "UTF-8"));
            JSONObject jsonObject = new JSONObject(jsonStr);
            jsonObject = jsonObject.getJSONObject("realtimeStationArrival");

            JSONArray jsonArray = new JSONArray();
            if (jsonObject.get("row") instanceof JSONArray) {
                jsonArray = jsonObject.getJSONArray("row");
            } else {
                jsonArray.put(jsonObject.getJSONObject("row"));
            }

            String result = "";
            int total = 0;

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                String subwayId = obj.get("subwayId").toString();
                String updnLine = obj.get("updnLine").toString();

                if (subwayId.equals(infoVO.getSubwayId()) && updnLine.equals(infoVO.getUpdnLine()) && total < 2) {
                    if (total == 0)
                        result += "{ \"arvlMsg1\" : \"" + obj.getString("arvlMsg2") + "\", ";
                    if (total == 1)
                        result += "\"arvlMsg2\" : \"" + obj.getString("arvlMsg2") + "\" }";
                    total++;
                }
            }

            if (total == 1)
                return result + "}";
            if (total == 2)
                return result;

            return "no such subwayId or stationId";

        } catch (Exception e) {
            log.error(e.getMessage() + e.getStackTrace());
            return "api call error";
        }
    }

    public String getCurrentWeatherData(WeatherSearchInfoDto infoVO) {
        try {

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


            String result = "{";

            for (int i = 0; i < 10; i++) {
                JSONObject obj = jsonArray.getJSONObject(i);

                if (obj.getString("category").equals("T1H"))
                    result += "\"기온\" : \"" + obj.getInt("fcstValue") + "\" ,";
                else if (obj.getString("category").equals("REH"))
                    result += "\"습도\" : \"" + obj.getInt("fcstValue") + "%\" ,";
                else if (obj.getString("category").equals("SKY"))
                    result += "\"하늘상태\" : \"" + SKY_STATUS[obj.getInt("fcstValue")] + "\" ,";
                else if (obj.getString("category").equals("PTY"))
                    result += "\"강수형태\" : \"" + PTY_STATUS[obj.getInt("fcstValue")] + "\" ,";

            }

            result = result.substring(0, result.length() - 2);
            result += "}";

            return result;

        } catch (Exception e) {
            log.error(e.getMessage() + e.getStackTrace());
            return "api call error";
        }
    }

    public String getCategoryResult(String user, String category, CategorySearchInfoDto infoVO) {
        return getInformationFromPython(user, category, infoVO.getArgs());
    }

    //프로젝트 기준 경로 파일 이름 반환 (수정 필요)
    public String getCategoryFilename(String user, String category) {
        return "src/main/java/team/kelly/kellyserver/category/pyresource/" + user + "/" + category + ".py";
    }

    //파이썬 args 넘기기 전 Str형 전처리
    public String preArgsToStr(List<String> args) {
        String str = "[";
        for (String item : args) {
            str += "'" + item + "', "; //앞 뒤로 문자처리
        }
        str += "]";
        return str;
    }

    //파이썬 파일에서 데이터 가져옴
    public String getInformationFromPython(String user, String category, List<String> args) {

        try (Interpreter interp = new SharedInterpreter()) {
            interp.eval("args = " + preArgsToStr(args));

            log.info(String.valueOf(preArgsToStr(args)));

            interp.runScript(getCategoryFilename(user, category));
            interp.eval("from java.lang import System");
            Object output = interp.getValue("output");

            return output.toString();

        } catch (JepException e) {
            e.printStackTrace();
            return "Python File Internal Error";
        }
    }
}
