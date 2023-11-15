package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.user.domain.UserLogin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JoinDto {

  @JsonProperty("email")
  private String email;

  @JsonProperty("username")
  private String userName;

  @JsonProperty("password")
  private String password;

  @JsonProperty("password-confirm")
  private String passwordConfirm;

  public UserLogin toEntity() {
    return UserLogin.builder()
        .email(email)
        .userName(userName)
        .build();
  }
}
