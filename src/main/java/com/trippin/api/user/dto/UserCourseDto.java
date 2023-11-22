package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.course.domain.Course;
import com.trippin.api.user.domain.UserCourse;
import com.trippin.api.user.domain.UserLogin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 코스")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCourseDto {

  @ApiModelProperty(value = "유저 아이디")
  @JsonProperty("user-id")
  private Long userLoginId;

  @ApiModelProperty(value = "코스 아이디")
  @JsonProperty("course-id")
  private Long courseId;

  public UserCourse toEntity(UserLogin userLoginId, Course courseId) {
    return UserCourse.builder()
        .userLoginId(userLoginId)
        .courseId(courseId)
        .build();
  }
}
