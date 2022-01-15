package team.kelly.kellyserver.navigation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalkNodeDto extends PathNode {

    int walkMeter;

    public WalkNodeDto() {
        this.setTransportation("walk");
    }

    public void setData(int walkMeter) {
        this.walkMeter = walkMeter;
    }

}
