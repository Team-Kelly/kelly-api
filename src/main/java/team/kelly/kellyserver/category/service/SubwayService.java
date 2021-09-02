package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.SubwayResultInfoDto;
import team.kelly.kellyserver.category.dto.SubwaySearchInfoDto;
import team.kelly.kellyserver.common.ApiUtility;

import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@Service
public class SubwayService {

    @Value("${apikey.seouloepnapi}")
    String seoulOpenApiKey;
    static final String subwayUrlPrefix = "http://swopenapi.seoul.go.kr/api/subway/";
    static final String subwayUrlSuffix = "/xml/realtimeStationArrival/0/1000/";

    public SubwayResultInfoDto getSubwayArriveData(SubwaySearchInfoDto infoVO) throws IOException {

        SubwayResultInfoDto result = new SubwayResultInfoDto();

        String jsonStr = ApiUtility.callApi(subwayUrlPrefix + seoulOpenApiKey + subwayUrlSuffix + URLEncoder.encode(infoVO.getStatnNm(), "UTF-8"));
        JSONObject jsonObject = new JSONObject(jsonStr);
        jsonObject = jsonObject.getJSONObject("realtimeStationArrival");

        JSONArray jsonArray = new JSONArray();
        if (jsonObject.get("row") instanceof JSONArray) {
            jsonArray = jsonObject.getJSONArray("row");
        } else {
            jsonArray.put(jsonObject.getJSONObject("row"));
        }

        int total = 0;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String subwayId = obj.get("subwayId").toString();
            String updnLine = obj.get("updnLine").toString();

            if (subwayId.equals(infoVO.getSubwayId()) && updnLine.equals(infoVO.getUpdnLine()) && total < 2) {
                if (total == 0)
                    result.setArrmsg1(obj.getString("arvlMsg2"));
                if (total == 1)
                    result.setArrmsg2(obj.getString("arvlMsg2"));
                total++;
            }
        }

        log.info(result.toString());

        return result;

//        } catch (Exception e) {
//            log.error(e.getMessage() + e.getStackTrace());
//        }

    }

}
