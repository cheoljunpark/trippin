package com.trippin.api.user.domain;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.crypto.password.PasswordEncoder;

// 암호화 출처: https://hou27.tistory.com/entry/Spring-Boot-Spring-Security-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0-%EC%95%94%ED%98%B8%ED%99%94

@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "auth_password")
public class AuthPassword {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

//  @Column(name = "user_id", nullable = false)
//  private Long userId;

  @OneToOne
  @JoinColumn(name = "user_id")
  private UserLogin userId;

  @Column(name = "password", nullable = false)
  private String password;

  @CreationTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "update_date", nullable = false)
  private Date updateDate;

  /**
   * 비밀번호를 암호화
   *
   * @param passwordEncoder 암호화 할 인코더 클래스
   * @return 변경된 유저 Entity
   */
  public AuthPassword hashPassword(PasswordEncoder passwordEncoder) {
    this.password = passwordEncoder.encode(this.password);
    return this;
  }

  /**
   * 비밀번호 확인
   *
   * @param plainPassword   암호화 이전의 비밀번호
   * @param passwordEncoder 암호화에 사용된 클래스
   * @return true | false
   */
  public boolean checkPassword(String plainPassword, PasswordEncoder passwordEncoder) {
    return passwordEncoder.matches(plainPassword, this.password);
  }
}
