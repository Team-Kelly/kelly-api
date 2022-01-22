package team.kelly.kellyserver.common.utility;

public class ConvertUtility {
    public static String convertSecToTime(int paramSec) {
        int min = paramSec / 60;
        int sec = paramSec % 60;

        if (min == 0) {
            return sec + "초";
        } else {
            return min + "분 " + sec + "초";
        }

    }

    public static String convertOdsaySubwayCodeToLocalCode(int subwayCode) {

        switch (subwayCode) {
            case 1: //서울 1~9호선
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
                return "100" + subwayCode;
            case 101: //공항철도
                return "1065";
            case 102: //자기부상철도
                return "1091";
            case 104: //경의중앙선
                return "1063";
            case 108: //경춘선
                return "1067";
            case 109: //신분당선
                return "1077";
            case 113: //우이신설
                return "1092";
            case 116: //수인분당
                return "1075";
            default:
                return null;
        }
    }

}
