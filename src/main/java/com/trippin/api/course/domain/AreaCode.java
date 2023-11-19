package com.trippin.api.course.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "area_code")
public class AreaCode {

  @Id
  @Column(nullable = false, length = 5)
  private String code;

  @Column(name = "name", length = 50)
  private String name;
}
