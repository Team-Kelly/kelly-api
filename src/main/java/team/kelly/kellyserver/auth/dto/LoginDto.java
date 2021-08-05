package team.kelly.kellyserver.auth.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @NotNull
    @Size(min = 3, max = 50)
    @ApiModelProperty(example = "testid")
    private String username;

    @NotNull
    @Size(min = 3, max = 100)
    @ApiModelProperty(example = "testpwd")
    private String password;
}