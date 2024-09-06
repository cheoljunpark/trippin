package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.user.domain.UserCourseSpot;
import com.trippin.api.user.domain.UserCourseSpotArticle;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 방문 관광지 후기")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseSpotArticleDto {

  @ApiModelProperty(value = "유저 관광지 아이디")
  @JsonProperty("user-course-spot-id")
  private Long userCourseSpotId;

  @ApiModelProperty(value = "내용")
  @JsonProperty("content")
  private String content;


  public UserCourseSpotArticle toEntity(UserCourseSpot userCourseSpot) {
    return UserCourseSpotArticle.builder()
        .userCourseSpotId(userCourseSpot)
        .content(content)
        .build();
  }
}
