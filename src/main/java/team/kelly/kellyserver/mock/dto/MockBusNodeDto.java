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
