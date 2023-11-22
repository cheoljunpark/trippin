package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "로그인 응답")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginResponseDto {

  @ApiModelProperty(value = "유저 네임", example = "ssafy", required = true)
  @JsonProperty("userName")
  private String userName;

  @ApiModelProperty(value = "유저 이메일", example = "ssafy@gmail.com", required = true)
  @JsonProperty("email")
  private String email;

  @ApiModelProperty(value = "토큰", required = true)
  @JsonProperty("token")
  private String token;

}
