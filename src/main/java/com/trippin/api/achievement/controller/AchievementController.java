package com.trippin.api.achievement.controller;

import static com.trippin.api.response.JSendResponseEntity.success;

import com.trippin.api.achievement.dto.AchievementDto;
import com.trippin.api.achievement.dto.AchievementTypeDto;
import com.trippin.api.achievement.service.AchievementService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "업적")
@RestController
@RequiredArgsConstructor
@RequestMapping("${api.version}/achievements")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class AchievementController {

  private final AchievementService achievementService;

  @ApiOperation(
      value = "업적 등록 api",
      notes = "업적을 등록하는 API")
  @PostMapping("/achievement")
  public ResponseEntity<?> achievement(@RequestBody AchievementDto achievementDto) {
    return ResponseEntity.ok(success(achievementService.postAchievement(achievementDto)));
  }

  @ApiOperation(
      value = "업적 조회 api",
      notes = "업적을 조회하는 API")
  @GetMapping("/achievement/{achievementId}")
  public ResponseEntity<?> achievement(@PathVariable Long achievementId) {
    return ResponseEntity.ok(success(achievementService.getAchievement(achievementId)));
  }

  @ApiOperation(
      value = "업적 타입 등록 api",
      notes = "업적 타입을 등록하는 API")
  @PostMapping("/achievementType")
  public ResponseEntity<?> achievementType(@RequestBody AchievementTypeDto achievementTypeDto) {
    return ResponseEntity.ok(success(achievementService.postAchievementType(achievementTypeDto)));
  }

  @ApiOperation(
      value = "업적 타입 조회 api",
      notes = "업적 타입을 조회하는 API")
  @GetMapping("/achievementType/{achievementTypeId}")
  public ResponseEntity<?> achievementType(@PathVariable Long achievementTypeId) {
    return ResponseEntity.ok(success(achievementService.getAchievementType(achievementTypeId)));
  }

}
