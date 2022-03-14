package team.kelly.kellyserver.common;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Getter
@Setter
public class GlobalValue {

    private static final int MAX_ODSAY_CALL = 850;
    private static final int MAX_WEATHER_CALL = 990;
    private int odsayApiCallCount;
    private int weatherApiCallCount;

    private GlobalValue() {
        odsayApiCallCount = 0;
        weatherApiCallCount = 0;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void resetOdsayApiCallCount() {
        odsayApiCallCount = 0;

        log.info("odsay api 호출 횟수가 초기화되었습니다.");
    }

    @Scheduled(cron = "0 0 9 * * *")
    private void resetWeatherApiCallCount() {
        weatherApiCallCount = 0;

        log.info("open weather api 호출 횟수가 초기화되었습니다.");
    }

    public boolean isOdsayCallExceed() {
        if (odsayApiCallCount > MAX_ODSAY_CALL) {
            return true;
        } else {
            return false;
        }
    }

    public void increaseOdsayCallCount() {
        odsayApiCallCount += 1;
    }

    public boolean isWeatherCallExceed() {
        if (weatherApiCallCount > MAX_WEATHER_CALL) {
            return true;
        } else {
            return false;
        }
    }

    public void increaseWeatherCallCount() {
        weatherApiCallCount += 1;
    }

}
