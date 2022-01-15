package team.kelly.kellyserver.navigation.dto;

import lombok.Data;

@Data
public class RouteSearchDto {
    String startX;
    String startY;
    String endX;
    String endY;
    String option;
}
