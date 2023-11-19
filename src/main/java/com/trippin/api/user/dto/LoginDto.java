package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.user.domain.UserLogin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "로그인")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginDto {

  @ApiModelProperty(value = "유저 이메일", example = "ssafy@gmail.com", required = true)
  @JsonProperty("email")
  private String email;

  @ApiModelProperty(value = "유저 네임", example = "ssafy", required = true)
  @JsonProperty("username")
  private String userName;

  @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
  @JsonProperty("password")
  private String password;

  public UserLogin toEntity() {
    return UserLogin.builder()
        .email(email)
        .userName(userName)
        .build();
  }
}

