package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.BusResultDto;
import team.kelly.kellyserver.category.dto.BusSearchDto;
import team.kelly.kellyserver.common.utility.ApiUtility;

import java.io.IOException;

@Slf4j
@Service
public class BusService {

    @Value("${apikey.govoepnapi}")
    String govOpenApiKey;
    static final String busUrl = "https://bus.go.kr/xmlRequest/getStationByUid.jsp?strBusNumber=";

    public BusResultDto getBusArriveData(BusSearchDto infoVO) throws IOException {

        BusResultDto result = new BusResultDto();

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

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String rtNm = obj.get("rtNm").toString();

            if (rtNm.equals(infoVO.getBusNumber())) {
                result.setArrmsg1(obj.getString("arrmsg1"));
                result.setArrmsg2(obj.getString("arrmsg2"));
            }
        }

        return result;

//        } catch (Exception e) {
//            log.error(e.getMessage() + e.getStackTrace());
//            return "api call error";
//        }
    }

}
