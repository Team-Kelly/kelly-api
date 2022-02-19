package team.kelly.kellyserver.navigation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusNodeDto extends PathNode {

    static final String[] BUS_TYPE = {"없음", "일반", "좌석", "마을버스", "직행좌석", "공항버스", "간선급행", "없음", "없음", "없음", "외곽", "간선", "지선", "순환", "광역", "급행", "없음", "없음", "없음", "없음", "농어촌버스", "제주도 시외형버스", "경기도 시외형버스", "없음", "없음", "없음", "급행간선"};
    String startStationName; //출발 정류장 이름
    String endStationName; //도착 정류장 이름
    int stationCnt; //거쳐가는 정류장 개수
    String busName; //버스 번호 이름
    String busId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 버스 id
    int busType; //해당 bus의 타입
    String busTypeDetail; //해당 station의 지역코드
    String startStationId; //오픈api에서 도착 잔여 시간 조회를 위한 고유 정류장 id
    String cityCode; //해당 station의 지역코드

    public BusNodeDto() {
        this.setTransportation("bus");
    }

    public void setData(String startStationName, String endStationName, int stationCnt, String busName, String busId, int busType, String startStationId, String cityCode) {
        this.startStationName = startStationName;
        this.endStationName = endStationName;
        this.stationCnt = stationCnt;
        this.busName = busName;
        this.busId = busId;
        this.busType = busType;
        this.busTypeDetail = getBusDetail(busType);
        this.startStationId = startStationId;
        this.cityCode = cityCode;
    }

    private String getBusDetail(int busType) {
        return BUS_TYPE[busType];
    }

}
