package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.user.domain.UserPrivacy;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 개인정보")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserPrivacyDto {

  @ApiModelProperty(value = "휴대전화번호", example = "01012345678")
  @JsonProperty("cell-phone")
  private String cellPhone;

  @ApiModelProperty(value = "생년월일", example = "2023-01-01")
  @JsonProperty("birthday")
  private Date birthday;

  public UserPrivacy toEntity(Long userId) {
    return UserPrivacy.builder()
        .userId(userId)
        .cellPhone(cellPhone)
        .birthday(birthday)
        .build();
  }
}
