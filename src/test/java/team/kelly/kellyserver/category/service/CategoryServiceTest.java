package team.kelly.kellyserver.category.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import team.kelly.kellyserver.category.dto.BusSearchInfoDto;
import team.kelly.kellyserver.category.dto.SubwaySearchInfoDto;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    CategoryService categoryService;

    @Test
    void getBusArriveData_잘_작동되는지() {

        BusSearchInfoDto given = new BusSearchInfoDto();
        given.setBusNumber("261");
        given.setStationNumber("11457");
        String actual = categoryService.getBusArriveData(given);

        log.info(actual);
        assertThat(actual).isNotNull();
    }

    @Test
    void getSubwayArriveData_잘_작동되는지() {

        SubwaySearchInfoDto given = new SubwaySearchInfoDto();
        given.setSubwayId("1004");
        given.setStatnNm("서울");
        given.setUpdnLine("하행");
        given.setStatnId("1004000426");
        String actual = categoryService.getSubwayArriveData(given);

        log.info(actual);
        assertThat(actual).isNotNull();

    }
}