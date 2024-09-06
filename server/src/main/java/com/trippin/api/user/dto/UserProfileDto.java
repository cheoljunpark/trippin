package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.domain.UserProfile;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 프로필 정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserProfileDto {


  @ApiModelProperty(value = "자기소개", example = "안녕하세요. 저는 김싸피입니다.")
  @JsonProperty("introduction")
  private String introduction;

  @ApiModelProperty(value = "사진", example = "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAkGBxAQEBANDRIQDQ0NDQ0NDFag")
  @JsonProperty("image-url")
  private String imageUrl;

  public UserProfile toEntity(UserLogin userLoginId) {
    return UserProfile.builder()
        .userLoginId(userLoginId)
        .introduction(introduction)
        .imageUrl(imageUrl)
        .build();
  }
}
