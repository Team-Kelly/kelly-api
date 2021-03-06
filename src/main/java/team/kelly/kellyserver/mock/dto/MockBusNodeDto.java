package team.kelly.kellyserver.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockBusNodeDto extends MockNode {

    String startStation;
    String endStation;
    int stationCnt;
    String busName;
    String busId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 버스 id
    String stationId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 정류장 id

    public MockBusNodeDto() {
        this.setTransportation("bus");
    }

    public void setData(String startStation, String endStation, int stationCnt, String busName) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.stationCnt = stationCnt;
        this.busName = busName;
    }

}
