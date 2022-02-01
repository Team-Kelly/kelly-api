package team.kelly.kellyserver.navigation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalkNodeDto extends PathNode {

    int walkMeter; //도보거리(단위: 미터)

    public WalkNodeDto() {
        this.setTransportation("walk");
    }

    public void setData(int walkMeter) {
        this.walkMeter = walkMeter;
    }

}
