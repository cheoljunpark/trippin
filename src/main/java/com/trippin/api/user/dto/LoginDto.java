package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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

  @ApiModelProperty(value = "유저 네임/이메일", example = "ssafy/ssafy@gmail.com", required = true)
  @JsonProperty("usernameOrEmail")
  private String usernameOrEmail;

  @ApiModelProperty(value = "비밀번호", example = "1234", required = true)
  @JsonProperty("password")
  private String password;

}

