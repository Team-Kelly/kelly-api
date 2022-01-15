package team.kelly.kellyserver.navigation.service;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import team.kelly.kellyserver.common.utility.ApiUtility;
import team.kelly.kellyserver.navigation.dto.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NavigationService {


    @Value("${apikey.odsay}")
    String odsayApiKey;
    static final String naviApi = "https://api.odsay.com/v1/api/searchPubTransPathR?apiKey=";

    public List<RouteDto> getNavigationRuote(RouteSearchDto routeSearchDto) throws IOException {

        String OPT = "0";
        String SearchPathType = "0";


        if (routeSearchDto.getOption().equals("1")) { //옵션 지하철
            OPT = "1";
            SearchPathType = "1";
        } else if (routeSearchDto.getOption().equals("2")) { //옵션 버스
            OPT = "1";
            SearchPathType = "2";
        }

        String jsonStr = ApiUtility.callApi(naviApi + odsayApiKey + "&lang=0&output=xml&SX=" + routeSearchDto.getStartX() + "&SY=" + routeSearchDto.getStartY() + "&EX=" + routeSearchDto.getEndX() + "&EY=" + routeSearchDto.getEndY() + "&OPT=" + OPT + "&SearchType=0&SearchPathType=" + SearchPathType);
        JSONObject jsonObject = new JSONObject(jsonStr);
        jsonObject = jsonObject.getJSONObject("message").getJSONObject("result");

        log.info(String.valueOf(jsonStr));

        JSONArray jsonPathArray = new JSONArray();
        if (jsonObject.get("path") instanceof JSONArray) {

            jsonPathArray = jsonObject.getJSONArray("path");
        } else {
            //탐색가능한 길찾기 경로 리스트
            jsonPathArray.put(jsonObject.getJSONObject("path"));
        }

        log.info(jsonPathArray.toString());

        List<RouteDto> routeDtoList = new ArrayList<>();


        for (int i = 0; i < jsonPathArray.length(); i++) {

            JSONObject obj = jsonPathArray.getJSONObject(i);

            //하나의 길찾기 경로에 대한 노드 리스트
            JSONArray jsonSubPathArray = obj.getJSONArray("subPath");

            int time = obj.getJSONObject("info").getInt("totalTime");
            List<PathNode> pathNodeList = new ArrayList<>();
            //하나의 경로
            for (int k = 1; k < jsonSubPathArray.length(); k++) {

                JSONObject node = jsonSubPathArray.getJSONObject(k);
                log.info(String.valueOf(node));

                if (node.getInt("trafficType") == 1) {//지하철

                    SubwayNodeDto subwayNodeDto = new SubwayNodeDto();
                    subwayNodeDto.setData(node.getString("startName"), node.getString("endName"), node.getInt("stationCount"), node.getJSONObject("lane").getString("name"), String.valueOf(node.getInt("startID")), String.valueOf(node.getInt("wayCode")), String.valueOf(node.getJSONObject("lane").getInt("subwayCityCode")));

                    pathNodeList.add(subwayNodeDto);

                } else if (node.getInt("trafficType") == 2) {//버스
                    BusNodeDto busNodeDto = new BusNodeDto();

                    JSONArray laneArray = new JSONArray();
                    if (node.get("lane") instanceof JSONArray) {

                        laneArray = node.getJSONArray("lane");
                    } else {
                        //탐색가능한 길찾기 경로 리스트
                        laneArray.put(node.getJSONObject("lane"));
                    }

                    busNodeDto.setData(node.getString("startName"), node.getString("endName"), node.getInt("stationCount"), laneArray.getJSONObject(0).get("busNo").toString(), laneArray.getJSONObject(0).get("busID").toString(), String.valueOf(node.getInt("startID")));

                    pathNodeList.add(busNodeDto);
                } else if (node.getInt("trafficType") == 3) {//도보
                    WalkNodeDto walkNodeDto = new WalkNodeDto();
                    walkNodeDto.setData(node.getInt("distance"));

                    if (walkNodeDto.getWalkMeter() == 0) {
                        continue;
                    }

                    pathNodeList.add(walkNodeDto);

                }

            }

            RouteDto routeDto = new RouteDto(pathNodeList, time);
            routeDtoList.add(routeDto);

        }

        return routeDtoList;

    }


}
