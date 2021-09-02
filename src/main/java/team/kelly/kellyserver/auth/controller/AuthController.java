package team.kelly.kellyserver.auth.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.kelly.kellyserver.auth.dto.LoginDto;
import team.kelly.kellyserver.auth.dto.TokenDto;
import team.kelly.kellyserver.auth.dto.TokenRequestDto;
import team.kelly.kellyserver.auth.service.AuthService;
import team.kelly.kellyserver.common.security.TokenProvider;

import javax.validation.Valid;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api", produces = "application/text; charset=utf8")
public class AuthController {

    private final AuthService authService;
    private final TokenProvider tokenProvider;


    @PostMapping("/authenticate")
    @ApiOperation(value = "엑세스 토큰 & 리프레시 토큰 발급", notes = "유저 데이터로 엑세스 토큰과 리프레시 토큰을 발급한다.")
    public ResponseEntity<TokenDto> authorize(@Valid @RequestBody LoginDto loginDto) {
        return ResponseEntity.ok(authService.authorize(loginDto));
    }

    @PostMapping("/reissue")
    @ApiOperation(value = "엑세스 토큰 & 리프레시 토큰 재발급", notes = "엑세스 토큰과 리프레시 토큰으로 새로운 엑세스 토큰과 리프레시 토큰을 발급한다.")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequestDto tokenRequestDto) {
        Authentication authentication = tokenProvider.getAuthentication(tokenRequestDto.getAccessToken());

        return ResponseEntity.ok(authService.reissue(tokenRequestDto));

    }
}