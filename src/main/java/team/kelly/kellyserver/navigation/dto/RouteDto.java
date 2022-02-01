package team.kelly.kellyserver.navigation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class RouteDto {
    List<PathNode> pathNodeList;
    int durationTime; //소요시간(분)
}
