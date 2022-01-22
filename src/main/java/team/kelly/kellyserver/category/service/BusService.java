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
    static final String seoulBusUrl = "https://bus.go.kr/xmlRequest/getStationByUid.jsp?strBusNumber=";
    static final String gyeonggiBusUrl = "http://apis.data.go.kr/6410000/busarrivalservice/getBusArrivalList?serviceKey=";


    public BusResultDto getBusArriveData(BusSearchDto infoVO) throws IOException {

        if (infoVO.getCityCode().equals("1000")) {
            return getSeoulBusArriveData(infoVO);
        } else {
            return getGyeonggiBusArriveData(infoVO);
        }

    }

    public BusResultDto getSeoulBusArriveData(BusSearchDto infoVO) throws IOException {

        BusResultDto result = new BusResultDto();

        String jsonStr = ApiUtility.callApi(seoulBusUrl + infoVO.getStationNumber());
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

            String rtId = obj.get("rtId").toString();

            if (rtId.equals(infoVO.getBusNumber())) {
                result.setArrmsg1(obj.getString("arrmsg1"));
                result.setArrmsg2(obj.getString("arrmsg2"));
            }
        }
        return result;
    }

    public BusResultDto getGyeonggiBusArriveData(BusSearchDto infoVO) throws IOException {

        BusResultDto result = new BusResultDto();

        String jsonStr = ApiUtility.callApi(gyeonggiBusUrl + govOpenApiKey + "&stationId=" + infoVO.getStationNumber());
        JSONObject jsonObject = new JSONObject(jsonStr);
        jsonObject = jsonObject.getJSONObject("response").getJSONObject("msgBody");

        log.info(String.valueOf(jsonStr));

        JSONArray jsonArray = new JSONArray();
        if (jsonObject.get("busArrivalList") instanceof JSONArray) {
            jsonArray = jsonObject.getJSONArray("busArrivalList");
        } else {
            jsonArray.put(jsonObject.getJSONObject("busArrivalList"));
        }

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);

            String routeId = obj.get("routeId").toString();

            if (routeId.equals(infoVO.getBusNumber())) {
                result.setArrmsg1(obj.get("predictTime1").toString() + "분");
                result.setArrmsg2(obj.get("predictTime2").toString() + "분");
            }
        }

        return result;

    }
}
