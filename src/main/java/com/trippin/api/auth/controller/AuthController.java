package com.trippin.api.auth.controller;

import static com.trippin.api.response.JSendResponseEntity.success;

import com.trippin.api.auth.service.AuthService;
import com.trippin.api.user.dto.LoginDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "인증, 인가")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/users")
public class AuthController {

  private final AuthService authService;

  @ApiOperation(
      value = "로그인 api",
      notes = "로그인 페이지에서 유저를 등록하는 API")
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    return ResponseEntity.ok(success(authService.login(loginDto)));
  }

  // TODO: 필요없음
  @ApiOperation(
      value = "로그아웃 api",
      notes = "유저 토큰을 삭제(?)하는 API")
  @PostMapping("/{username}/logout")
  public ResponseEntity<?> logout(@PathVariable String username) {
    authService.logout(username);
    return null;
  }
}
