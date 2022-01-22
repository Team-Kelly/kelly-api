package team.kelly.kellyserver.navigation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusNodeDto extends PathNode {

    String startStation;
    String endStation;
    int stationCnt;
    String busName;
    String busId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 버스 id
    String stationId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 정류장 id
    String cityCode; //해당 station의 지역코드

    public BusNodeDto() {
        this.setTransportation("bus");
    }

    public void setData(String startStation, String endStation, int stationCnt, String busName, String busId, String stationId, String cityCode) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.stationCnt = stationCnt;
        this.busName = busName;
        this.busId = busId;
        this.stationId = stationId;
        this.cityCode = cityCode;
    }

}
