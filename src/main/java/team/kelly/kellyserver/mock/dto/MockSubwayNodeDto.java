package team.kelly.kellyserver.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockSubwayNodeDto extends MockNode {

    String startStation;
    String endStation;
    int stationCnt;
    String lineName;
    String startStationId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 지하철역 id
    String direction; //오픈api에서 도착 잔여 시간 조회를 위한 상/하행 정보
    String lineId; //오픈api에서 도착 잔여 시간 조회를 위한 지하철호선 id

    public MockSubwayNodeDto() {
        this.setTransportation("subway");
    }

    public void setData(String startStation, String endStation, int stationCnt, String lineName) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.stationCnt = stationCnt;
        this.lineName = lineName;
        this.startStationId = "1234";
        this.direction = "상행";
        this.lineId = "10007";
    }

}
