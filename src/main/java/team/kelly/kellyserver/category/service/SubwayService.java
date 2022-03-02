package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.category.dto.SubwayResultDto;
import team.kelly.kellyserver.category.dto.SubwaySearchDto;
import team.kelly.kellyserver.common.utility.ApiUtility;
import team.kelly.kellyserver.common.utility.ConvertUtility;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class SubwayService {

    @Value("${apikey.seouloepnapi}")
    String seoulOpenApiKey;
    static final String subwayUrlPrefix = "http://swopenapi.seoul.go.kr/api/subway/";
    static final String subwayUrlSuffix = "/xml/realtimeStationArrival/0/1000/";

    public SubwayResultDto getSubwayArriveData(SubwaySearchDto infoVO) throws IOException, ParseException {

        SubwayResultDto result = new SubwayResultDto();

        String jsonStr = ApiUtility.callApi(subwayUrlPrefix + seoulOpenApiKey + subwayUrlSuffix + URLEncoder.encode(infoVO.getStationName(), "UTF-8"), "xml");
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
            String arvlMsg2 = obj.get("arvlMsg2").toString();
            String updnLine = obj.get("updnLine").toString();
            String arvlCd = obj.get("arvlCd").toString();
            String btrainSttus = "";
            if (!obj.isNull("btrainSttus")) {
                btrainSttus = obj.get("btrainSttus").toString();
            }
            String barvlDt = obj.get("barvlDt").toString(); //도착까지 남은 초
            String recptnDtStr = obj.get("recptnDt").toString();


            SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date recptnDtDate = transFormat.parse(recptnDtStr);
            Date nowDate = new Date();


            int arriveSec = Integer.parseInt(barvlDt);
            int delaySec = (int) ((nowDate.getTime() - recptnDtDate.getTime()) / 1000);
            int actualRemain = arriveSec - delaySec;

            if (subwayId.equals(infoVO.getLineId()) && updnLine.equals(infoVO.getDirection())
                    && !arvlCd.equals("0") && !arvlCd.equals("1") && !arvlCd.equals("2")
                    && !btrainSttus.equals("ITX") && !btrainSttus.equals("급행")
                    && total < 2) {
                if (total == 0)
                    if (actualRemain > 0) {
                        result.setArrmsg1(ConvertUtility.convertSecToTime(actualRemain));
                    } else {
                        result.setArrmsg1(arvlMsg2);
                    }
                if (total == 1)
                    if (actualRemain > 0) {
                        result.setArrmsg2(ConvertUtility.convertSecToTime(actualRemain));
                    } else {
                        result.setArrmsg2(arvlMsg2);
                    }
                total++;
            }
        }

        log.info(result.toString());

        return result;

    }

}
