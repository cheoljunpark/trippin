package com.trippin.api.achievement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.achievement.domain.Achievement;
import com.trippin.api.achievement.domain.AchievementType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "업적")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementDto {

  @ApiModelProperty(value = "타입", example = "1", required = true)
  @JsonProperty("achievement-type")
  private Long achievementType;

  @ApiModelProperty(value = "이름", example = "업적1 제목", required = true)
  @JsonProperty("title")
  private String title;

  @ApiModelProperty(value = "설명", example = "업적1 설명", required = true)
  @JsonProperty("description")
  private String description;

  @ApiModelProperty(value = "달성조건", example = "1", required = true)
  @JsonProperty("goal")
  private int goal;

  @ApiModelProperty(value = "이미지", example = "업적1 이미지 경로")
  @JsonProperty("image-url")
  private String imageUrl;

  public Achievement toEntity(AchievementType achievementType) {
    return Achievement.builder()
        .achievementType(achievementType)
        .title(title)
        .description(description)
        .goal(goal)
        .imageUrl(imageUrl)
        .build();
  }
}
