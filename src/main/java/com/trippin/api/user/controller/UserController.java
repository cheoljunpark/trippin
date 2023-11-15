package com.trippin.api.user.controller;

import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.LoginDto;
import com.trippin.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @PostMapping("/join")
  public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
    return ResponseEntity.ok(userService.join(joinDto));
  }

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    return ResponseEntity.ok(userService.login(loginDto));
  }
}
