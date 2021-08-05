package team.kelly.kellyserver.category.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class CategorySearchInfoDto {

    @ApiModelProperty(example = "1")
    private int argc;
    @ApiModelProperty(example = "[\"남양주시\"]")
    private List<String> args;

}
