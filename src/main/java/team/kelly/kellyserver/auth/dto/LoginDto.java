package team.kelly.kellyserver.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @ApiModelProperty(example = "testid")
    private String username;

    @NotNull
    @ApiModelProperty(example = "testpwd")
    private String password;
}