package team.kelly.kellyserver.mock.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MockRouteDto {
    List<MockNode> NodeList;
    int durationTime;
}
