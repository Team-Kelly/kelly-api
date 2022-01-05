package team.kelly.kellyserver.mock;

import team.kelly.kellyserver.mock.dto.*;

import java.util.Arrays;
import java.util.List;

public class RouteDataGenerator {

    public static MockRouteDto get1() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }


    public static MockRouteDto get2() {

        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("동대문역", "회기역", 5, "1호선");
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("회기앞1번출구정류장", "마석역", 30, "1330-4");
        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(555);
        MockBusNodeDto mockBusNodeDto2 = new MockBusNodeDto();
        mockBusNodeDto2.setData("이디야앞정류장", "화도파출소정류장", 9, "경기N14");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(123);

        List<MockNode> mockNodeList = Arrays.asList(mockSubwayNodeDto1, mockBusNodeDto1, mockWalkNodeDto1, mockBusNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 350);

        return mockRouteDto1;

    }

    public static MockRouteDto get3() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("짱짱짱길어서안보이면어떡하지걱정되는정류장", "이름이엄청길고짱짱짱길어서안보일수도있을거같은정류장", 100, "버스번호가엄청길거같은버스번호");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("항공역", "광운대역", 5, "항공철도선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("광운대역", "가천대역", 10, "에버랜드호선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(34);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 20);
        return mockRouteDto1;

    }


    public static MockRouteDto get4() {

        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("나가자마자정류장", "좀걸었다가지하철도착하는정류장", 2, "경기1449-3");
        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(1224);
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("지하철역", "에버랜드역", 10, "8호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("에버랜드역", "상봉역", 19, "경의중앙선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(1234);

        List<MockNode> mockNodeList = Arrays.asList(mockBusNodeDto1, mockWalkNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 177);
        return mockRouteDto1;

    }


    public static MockRouteDto get5() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }


    public static MockRouteDto get6() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }

    public static MockRouteDto get7() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }


    public static MockRouteDto get8() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }


    public static MockRouteDto get9() {

        MockWalkNodeDto mockWalkNodeDto1 = new MockWalkNodeDto();
        mockWalkNodeDto1.setData(100);
        MockBusNodeDto mockBusNodeDto1 = new MockBusNodeDto();
        mockBusNodeDto1.setData("영진그린필아파트", "잠실역", 4, "M2316");
        MockSubwayNodeDto mockSubwayNodeDto1 = new MockSubwayNodeDto();
        mockSubwayNodeDto1.setData("잠실역", "강남역", 5, "2호선");
        MockSubwayNodeDto mockSubwayNodeDto2 = new MockSubwayNodeDto();
        mockSubwayNodeDto2.setData("강남역", "테스트역", 12, "경춘선");
        MockWalkNodeDto mockWalkNodeDto2 = new MockWalkNodeDto();
        mockWalkNodeDto2.setData(126);

        List<MockNode> mockNodeList = Arrays.asList(mockWalkNodeDto1, mockBusNodeDto1, mockSubwayNodeDto1, mockSubwayNodeDto2, mockWalkNodeDto2);
        MockRouteDto mockRouteDto1 = new MockRouteDto(mockNodeList, 170);
        return mockRouteDto1;

    }

}
