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
@Table(name = "user_login")
public class UserLogin {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "email", nullable = false, length = 50)
  private String email;

  @Column(name = "username", nullable = false, length = 20)
  private String userName;

//  @Column(name = "token")
//  private String token;
//
//  @OneToOne(cascade = CascadeType.PERSIST)
//  @JoinColumn(name = "user_privacy_id")
//  private UserPrivacy userPrivacy;
//
//  @OneToOne(cascade = CascadeType.PERSIST)
//  @JoinColumn(name = "user_profile_id")
//  private UserProfile userProfile;
//
//  @OneToMany(mappedBy = "userLogin", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//  private List<Course> courses = new ArrayList<>();
//
//  @OneToMany(mappedBy = "userLogin", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//  private List<UserAchievement> userAchievements = new ArrayList<>();

}
