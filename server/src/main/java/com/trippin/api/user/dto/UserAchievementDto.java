package com.trippin.api.user.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.achievement.domain.Achievement;
import com.trippin.api.user.domain.UserAchievement;
import com.trippin.api.user.domain.UserLogin;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "유저 업적")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAchievementDto {

  @ApiModelProperty(value = "업적 아이디", example = "1")
  @JsonProperty("achievement-id")
  private Long achievementId;


  @ApiModelProperty(value = "달성률", example = "1")
  @JsonProperty("progress")
  private int progress;

  @ApiModelProperty(value = "시작 날짜")
  @JsonProperty("start-date")
  private Date startDate;

  @ApiModelProperty(value = "달성 날짜")
  @JsonProperty("achieve-date")
  private Date achieveDate;

  public UserAchievement toEntity(Achievement achievementId, UserLogin userLoginId) {
    return UserAchievement.builder()
        .achievementId(achievementId)
        .userLoginId(userLoginId)
        .progress(progress)
        .startDate(startDate)
        .achieveDate(achieveDate)
        .build();
  }

}
