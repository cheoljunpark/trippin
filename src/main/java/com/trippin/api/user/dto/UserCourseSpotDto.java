package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.course.domain.Spot;
import com.trippin.api.user.domain.UserCourse;
import com.trippin.api.user.domain.UserCourseSpot;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 코스별 방문 관광지")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseSpotDto {

  @ApiModelProperty(value = "유저 코스 아이디")
  @JsonProperty("user-cousrse-id")
  private Long userCourseId;

  @ApiModelProperty(value = "관광지 아이디")
  @JsonProperty("spot-id")
  private Long spotId;

  public UserCourseSpot toEntity(UserCourse userCourse, Spot spot) {
    return UserCourseSpot.builder()
        .userCourseId(userCourse)
        .spotId(spot)
        .build();
  }
}
