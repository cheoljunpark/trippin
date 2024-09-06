package com.trippin.api.achievement.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "achievement")
@JsonIgnoreProperties({"id"})
public class Achievement {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "achievement_type")
  private AchievementType achievementType;

  @Column(name = "title", nullable = false, length = 50)
  private String title;

  @Column(name = "description", nullable = false, length = 200)
  private String description;

  @Column(name = "goal", nullable = false)
  private int goal;

  @Column(name = "image_url", length = 200)
  private String imageUrl;
}
