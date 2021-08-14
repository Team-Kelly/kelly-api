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
import team.kelly.kellyserver.common.ApiUtility;

import java.util.List;

@Slf4j
@Service
public class CategoryService {

    @Value("${apikey.seouloepnapi}")
    String seoulOpenApiKey;
    static final String busUrl = "https://bus.go.kr/xmlRequest/getStationByUid.jsp?strBusNumber=";
    static final String subwayUrlPrefix = "http://swopenapi.seoul.go.kr/api/subway/";
    static final String subwayUrlSuffix = "/xml/realtimeStationArrival/0/1000/";

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
                log.info(String.valueOf(obj));

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
            String jsonStr = ApiUtility.callApi(subwayUrlPrefix + seoulOpenApiKey + subwayUrlSuffix + infoVO.getStatnNm());
            JSONObject jsonObject = new JSONObject(jsonStr);
            jsonObject = jsonObject.getJSONObject("realtimeStationArrival");

            log.info(String.valueOf(jsonStr));

            JSONArray jsonArray = new JSONArray();
            if (jsonObject.get("row") instanceof JSONArray) {
                jsonArray = jsonObject.getJSONArray("row");
            } else {
                jsonArray.put(jsonObject.getJSONObject("row"));
            }

            String result = "";

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                log.info(String.valueOf(obj));

                String subwayId = obj.get("subwayId").toString();
                String statnId = obj.get("statnId").toString();
                String updnLine = obj.get("updnLine").toString();

                if (subwayId.equals(infoVO.getSubwayId()) && statnId.equals(infoVO.getStatnId()) && updnLine.equals(infoVO.getUpdnLine())) {
                    result += "{ \"arvlMsg1\" : \"" + obj.getString("arvlMsg2") + "\", " +
                            "\"arvlMsg2\" : \"" + obj.getString("arvlMsg3") + "\" }";
                    log.info(result);
                    return result;
                }
            }

            return "no such subwayId or stationId";

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
