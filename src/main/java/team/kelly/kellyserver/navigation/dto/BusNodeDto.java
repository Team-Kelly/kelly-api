package team.kelly.kellyserver.navigation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusNodeDto extends PathNode {

    String startStationName; //출발 정류장 이름
    String endStationName; //도착 정류장 이름
    int stationCnt; //거쳐가는 정류장 개수
    String busName; //버스 번호 이름
    String busId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 버스 id
    String startStationId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 정류장 id
    String cityCode; //해당 station의 지역코드

    public BusNodeDto() {
        this.setTransportation("bus");
    }

    public void setData(String startStationName, String endStationName, int stationCnt, String busName, String busId, String startStationId, String cityCode) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.stationCnt = stationCnt;
        this.busName = busName;
        this.busId = busId;
        this.startStationId = startStationId;
        this.cityCode = cityCode;
    }

}
