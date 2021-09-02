package team.kelly.kellyserver.common.utility;

import lombok.extern.slf4j.Slf4j;
import team.kelly.kellyserver.category.dto.GridValueDto;

/*
출처 : https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=tkddlf4209&logNo=220632424141
 */
@Slf4j
public class TransLocalPoint {

    static final double RE = 6371.00877; // 지구 반경(km)
    static final double GRID = 5.0; // 격자 간격(km)
    static final double SLAT1 = 30.0; // 투영 위도1(degree)
    static final double SLAT2 = 60.0; // 투영 위도2(degree)
    static final double OLON = 126.0; // 기준점 경도(degree)
    static final double OLAT = 38.0; // 기준점 위도(degree)
    static final double XO = 43; // 기준점 X좌표(GRID)
    static final double YO = 136; // 기1준점 Y좌표(GRID)

    public static GridValueDto getGridxy(double v1, double v2) {

        log.info("receive lat, lon : " + v1 + ", " + v2);

        GridValueDto dto = new GridValueDto();

        double DEGRAD = Math.PI / 180.0;
        // double RADDEG = 180.0 / Math.PI;

        double re = RE / GRID;
        double slat1 = SLAT1 * DEGRAD;
        double slat2 = SLAT2 * DEGRAD;
        double olon = OLON * DEGRAD;
        double olat = OLAT * DEGRAD;

        double sn = Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(sn);
        double sf = Math.tan(Math.PI * 0.25 + slat1 * 0.5);
        sf = Math.pow(sf, sn) * Math.cos(slat1) / sn;
        double ro = Math.tan(Math.PI * 0.25 + olat * 0.5);
        ro = re * sf / Math.pow(ro, sn);
        double ra = Math.tan(Math.PI * 0.25 + (v1) * DEGRAD * 0.5);
        ra = re * sf / Math.pow(ra, sn);
        double theta = v2 * DEGRAD - olon;
        if (theta > Math.PI)
            theta -= 2.0 * Math.PI;
        if (theta < -Math.PI)
            theta += 2.0 * Math.PI;
        theta *= sn;

        dto.setGridx((int) (Math.floor(ra * Math.sin(theta) + XO + 0.5)));
        dto.setGridy((int) (Math.floor(ro - ra * Math.cos(theta) + YO + 0.5)));

        return dto;
    }
}
