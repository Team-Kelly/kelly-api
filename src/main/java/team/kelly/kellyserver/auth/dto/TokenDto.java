package team.kelly.kellyserver.auth.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
public class TokenDto {

    private String accessToken;
    private String refreshToken;
    private String grantType;
    private long accessTokenExpiresIn;

    @Builder
    public TokenDto(String accessToken, String refreshToken, String grantType, long accessTokenExpiresIn) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.grantType = grantType;
        this.accessTokenExpiresIn = accessTokenExpiresIn;
    }

}