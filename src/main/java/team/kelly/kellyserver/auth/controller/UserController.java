package team.kelly.kellyserver.auth.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team.kelly.kellyserver.auth.dto.UserDto;
import team.kelly.kellyserver.auth.entity.User;
import team.kelly.kellyserver.auth.service.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    @ApiOperation(value="회원가입", notes="회원가입을 한다.")
    public ResponseEntity<User> signup(
            @Valid @RequestBody UserDto userDto
    ) {
        return ResponseEntity.ok(userService.signup(userDto));
    }

    @GetMapping("/user")
    @ApiOperation(value="본인 유저 정보 조회", notes="자신의 유저 정보를 조회한다.")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public ResponseEntity<User> getMyUserInfo() {
        return ResponseEntity.ok(userService.getMyUserWithAuthorities().get());
    }

    @GetMapping("/user/{username}")
    @ApiOperation(value="특정 유저 정보 조회", notes="가입된 유저 중 특정 유저 정보를 조회한다.")
    @PreAuthorize("hasAnyRole('ADMIN')") //ADMIN 권한만 호출 가능
    public ResponseEntity<User> getUserInfo(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserWithAuthorities(username).get());
    }
}