package com.trippin.api.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "user_course_spot")
public class UserCourseSpot {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_course_id", nullable = false)
  private int userCourseId;

  @Column(name = "spot", nullable = false)
  private String spot;

  @Column(name = "visit_date", nullable = false, columnDefinition = "DATETIME")
  private String visitDate;
}
