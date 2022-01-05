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

    public MockSubwayNodeDto() {
        this.setTransportation("subway");
    }

    public void setData(String startStation, String endStation, int stationCnt, String lineName) {
        this.startStation = startStation;
        this.endStation = endStation;
        this.stationCnt = stationCnt;
        this.lineName = lineName;
    }

}
