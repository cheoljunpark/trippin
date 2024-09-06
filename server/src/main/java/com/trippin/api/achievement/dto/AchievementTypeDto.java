package com.trippin.api.achievement.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trippin.api.achievement.domain.AchievementType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "업적 타입")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AchievementTypeDto {

  @ApiModelProperty(value = "타입", example = "1", required = true)
  @JsonProperty("type")
  private int type;

  public AchievementType toEntity() {
    return AchievementType.builder()
        .type(type)
        .build();
  }
}
