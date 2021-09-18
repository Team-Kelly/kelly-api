package team.kelly.kellyserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    //회원가입 시에 사용
    @NotNull
    @ApiModelProperty(example = "testid")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @ApiModelProperty(example = "testpwd")
    private String password;

    @NotNull
    @ApiModelProperty(example = "testname")
    private String nickname;
}