package team.kelly.kellyserver.mock.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MockWalkNodeDto extends MockNode {

    int walkMeter;

    public MockWalkNodeDto() {
        this.setTransportation("walk");
    }

    public void setData(int walkMeter) {
        this.walkMeter = walkMeter;
    }

}
