package team.kelly.kellyserver.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    //회원가입 시에 사용

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "testid")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(example = "testpwd")
    private String password;

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "testname")
    private String nickname;
}