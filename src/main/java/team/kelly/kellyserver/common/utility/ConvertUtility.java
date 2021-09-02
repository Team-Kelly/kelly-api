package team.kelly.kellyserver.common.utility;

public class ConvertUtility {
    public static String ConvertSecToTime(int paramSec) {
        int min = paramSec / 60;
        int sec = paramSec % 60;

        if (min == 0) {
            return sec + "초";
        } else {
            return min + "분 " + sec + "초";
        }

    }
}
