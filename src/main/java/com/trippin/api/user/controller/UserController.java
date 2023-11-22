package com.trippin.api.user.controller;

import static com.trippin.api.response.JSendResponseEntity.success;

import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.UserAchievementDto;
import com.trippin.api.user.dto.UserCourseDto;
import com.trippin.api.user.dto.UserCourseSpotArticleDto;
import com.trippin.api.user.dto.UserCourseSpotDto;
import com.trippin.api.user.dto.UserPrivacyDto;
import com.trippin.api.user.dto.UserProfileDto;
import com.trippin.api.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserController {

  private final UserService userService;

  @ApiOperation(
      value = "회원가입 api",
      notes = "회원가입 페이지에서 유저를 등록하는 API")
  @PostMapping
  public ResponseEntity<?> join(@RequestBody JoinDto joinDto) {
    return ResponseEntity.ok(success(userService.join(joinDto)));
  }

  @ApiOperation(
      value = "회원탈퇴 api",
      notes = "회원탈퇴 페이지에서 유저를 삭제하는 API")
  @DeleteMapping("/{username}")
  public ResponseEntity<?> delete(@PathVariable String username) {
    userService.delete(username);
    return (ResponseEntity<?>) ResponseEntity.ok();
  }

  @ApiOperation(
      value = "전체 유저 목록 조회 api",
      notes = "전체 유저들의 정보를 조회하는 API")
  @GetMapping("/")
  public ResponseEntity<?> getAllUsers() {
    return ResponseEntity.ok(success(
        userService.getAllUsers()));  // TODO: 유저의 이메일, 아이디, 토큰을 가져오고 있음, 다른 도메인이랑 조인해서 반환해야 함
  }


  // TODO: 개인 유저 전체 정보 조회
  @ApiOperation(
      value = "유저 전체 정보 조회 api",
      notes = "특정 유저의 전체 정보를 조회하는 API")
  @GetMapping("/{username}")
  public ResponseEntity<?> getUserInfo(@PathVariable String username) {
    return ResponseEntity.ok(success(userService.getUserinfo(username)));
  }

  @ApiOperation(
      value = "유저 개인정보 작성 api",
      notes = "특정 유저의 개인정보를 작성하는 API")
  @PostMapping("/{username}/privacy")
  public ResponseEntity<?> privacy(@PathVariable String username,
      @RequestBody UserPrivacyDto userPrivacyDto) {
    return ResponseEntity.ok(success(userService.postPrivacy(username, userPrivacyDto)));
  }

  @ApiOperation(
      value = "유저 개인정보 조회 api",
      notes = "특정 유저의 개인정보를 조회하는 API")
  @GetMapping("/{username}/privacy")
  public ResponseEntity<?> privacy(@PathVariable String username) {
    return ResponseEntity.ok(success(userService.getPrivacy(username)));
  }

  @ApiOperation(
      value = "유저 프로필 작성 api",
      notes = "특정 유저의 프로필을 작성하는 API")
  @PostMapping("{username}/profile")
  public ResponseEntity<?> postProfile(@PathVariable String username,
      @RequestBody UserProfileDto userProfileDto) {
    return ResponseEntity.ok(success(userService.postProfile(username, userProfileDto)));
  }

  @ApiOperation(
      value = "유저 프로필 수정 api",
      notes = "특정 유저의 프로필을 수정하는 API")
  @PutMapping("{username}/profile")
  public ResponseEntity<?> putProfile(@PathVariable String username,
      @RequestBody UserProfileDto userProfileDto) {
    return ResponseEntity.ok(success(userService.putProfile(username, userProfileDto)));
  }

  @ApiOperation(
      value = "유저 프로필 조회 api",
      notes = "특정 유저의 프로필을 조회하는 API")
  @GetMapping("{username}/profile")
  public ResponseEntity<?> profile(@PathVariable String username) {
    return ResponseEntity.ok(success(userService.getProfile(username)));
  }

  @ApiOperation(
      value = "유저 업적 등록 api",
      notes = "특정 유저의 업적을 등록하는 API")
  @PostMapping("{username}/achievement")
  public ResponseEntity<?> achievement(@PathVariable String username,
      @RequestBody UserAchievementDto userAchievementDto) {
    return ResponseEntity.ok(success(userService.postAchievement(username, userAchievementDto)));
  }

  @ApiOperation(
      value = "유저 코스 등록 api",
      notes = "특정 유저의 코스를 등록하는 API")
  @PostMapping("{username}/course")
  public ResponseEntity<?> course(@PathVariable String username,
      @RequestBody UserCourseDto userCourseDto) {
    return ResponseEntity.ok(success(userService.postCourse(username, userCourseDto)));
  }

  @ApiOperation(
      value = "유저 코스별 방문 관광지 등록 api",
      notes = "특정 유저의 코스별 방문한 관광지를 등록하는 API")
  @PostMapping("{username}/course/spot")
  public ResponseEntity<?> courseSpot(@PathVariable String username,
      @RequestBody UserCourseSpotDto userCourseSpotDto) {
    return ResponseEntity.ok(success(userService.postUserCourseSpot(username, userCourseSpotDto)));
  }

  @ApiOperation(
      value = "유저 방문 관광지 후기 등록 api",
      notes = "특정 유저의 방문 관광지 후기를 등록하는 API")
  @PostMapping("{username}/course/spot/article")
  public ResponseEntity<?> courseSpotArticle(@PathVariable String username,
      @RequestBody UserCourseSpotArticleDto userCourseSpotArticleDto) {
    return ResponseEntity.ok(
        success(userService.postUserCourseSpotArticle(username, userCourseSpotArticleDto)));
  }
}
