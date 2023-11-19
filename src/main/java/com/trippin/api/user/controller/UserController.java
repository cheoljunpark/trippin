package com.trippin.api.user.controller;

import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.LoginDto;
import com.trippin.api.user.dto.UserPrivacyDto;
import com.trippin.api.user.dto.UserProfileDto;
import com.trippin.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

  private final UserService userService;

  @ApiOperation(
      value = "회원가입 api",
      notes = "회원가입 페이지에서 유저를 등록하는 API")
  @PostMapping("/join")
  public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
    return ResponseEntity.ok(userService.join(joinDto));
  }

  @ApiOperation(
      value = "로그인 api",
      notes = "로그인 페이지에서 유저를 등록하는 API")
  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
    return ResponseEntity.ok(userService.login(loginDto));
  }

  @ApiOperation(
      value = "회원탈퇴 api",
      notes = "회원탈퇴 페이지에서 유저를 삭제하는 API")
  @DeleteMapping("/{username}")
  public ResponseEntity<Void> delete(@PathVariable String username) {
    userService.delete(username);
    return null;
  }

  @ApiOperation(
      value = "로그아웃 api",
      notes = "유저 토큰을 삭제(?)하는 API")
  @PostMapping("/logout")
  public ResponseEntity<?> logout() {
    // TODO: REDIS 사용해 로그아웃 구현
    return null;
  }

  @ApiOperation(
      value = "전체 유저 목록 조회 api",
      notes = "전체 유저들의 정보를 조회하는 API")
  @GetMapping("/")
  public ResponseEntity<List<?>> getAllUsers() {
    return ResponseEntity.ok(
        userService.getAllUsers());  // TODO: 유저의 이메일, 아이디, 토큰을 가져오고 있음, 다른 도메인이랑 조인해서 반환해야 함
  }


  // TODO: 개인 유저 전체 정보 조회
  @ApiOperation(
      value = "유저 전체 정보 조회 api",
      notes = "특정 유저의 전체 정보를 조회하는 API")
  @GetMapping("/{username}")
  public ResponseEntity<?> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(userService.getUserinfo(username));
  }

  @ApiOperation(
      value = "유저 팔로잉 수 조회 api",
      notes = "특정 유저의 팔로잉 수를 조회하는 API")
  @GetMapping("/{username}/following")
  public ResponseEntity<Integer> getFollowing(@PathVariable String username) {
    return ResponseEntity.ok(userService.getFollowing(username));
  }

  @ApiOperation(
      value = "유저 팔로워 수 조회 api",
      notes = "특정 유저의 팔로워 수를 조회하는 API")
  @GetMapping("/{username}/follower")
  public ResponseEntity<Integer> getFollower(@PathVariable String username) {
    return ResponseEntity.ok(userService.getFollower(username));
  }

  @ApiOperation(
      value = "유저 팔로잉 수 증가 api",
      notes = "특정 유저의 팔로잉 수를 증가하는 API")
  @PostMapping("/{username}/following")
  public ResponseEntity<?> postFollowing(@PathVariable String username) {
    return ResponseEntity.ok(userService.postFollowing(username));
  }

  @ApiOperation(
      value = "유저 팔로워 수 증가 api",
      notes = "특정 유저의 팔로워 수를 증가하는 API")
  @PostMapping("/{username}/follower")
  public ResponseEntity<?> postFollower(@PathVariable String username) {
    return ResponseEntity.ok(userService.postFollower(username));
  }

  @ApiOperation(
      value = "유저 개인정보 작성 api",
      notes = "특정 유저의 개인정보를 작성하는 API")
  @PostMapping("/{username}/privacy")
  public ResponseEntity<?> privacy(@PathVariable String username,
      @RequestBody UserPrivacyDto userPrivacyDto) {
    return ResponseEntity.ok(userService.putPrivacy(username, userPrivacyDto));
  }

  @ApiOperation(
      value = "유저 개인정보 조회 api",
      notes = "특정 유저의 개인정보를 조회하는 API")
  @GetMapping("/{username}/privacy")
  public ResponseEntity<?> privacy(@PathVariable String username) {
    return ResponseEntity.ok(userService.getPrivacy(username));
  }

  @ApiOperation(
      value = "유저 프로필 작성 api",
      notes = "특정 유저의 프로필을 작성하는 API")
  @PostMapping("{username}/profile")
  public ResponseEntity<?> profile(@PathVariable String username,
      @RequestBody UserProfileDto userProfileDto) {
    return ResponseEntity.ok(userService.putProfile(username, userProfileDto));
  }

  @ApiOperation(
      value = "유저 프로필 조회 api",
      notes = "특정 유저의 프로필을 조회하는 API")
  @GetMapping("{username}/profile")
  public ResponseEntity<?> profile(@PathVariable String username) {
    return ResponseEntity.ok(userService.getProfile(username));
  }
}
