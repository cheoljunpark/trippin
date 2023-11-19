package com.trippin.api.course.domain;

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
@Table(name = "course")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", length = 50)
  private String title;

  @Column(name = "distance", length = 10)
  private String distance;

  @Column(name = "address", length = 200)
  private String address;

  @Column(name = "mapx")
  private String mapx;

  @Column(name = "mapy")
  private String mapy;

  @Column(name = "image", length = 200)
  private String image;

  @Column(name = "category", length = 10)
  private String category;

  @Column(name = "overview", columnDefinition = "LONGTEXT")
  private String overview;

  @Column(name = "areaCode", length = 5)
  private String areaCode;

  @Column(name = "sigunguCode", length = 5)
  private String sigunguCode;
}
