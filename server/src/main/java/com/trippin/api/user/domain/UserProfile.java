package com.trippin.api.user.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
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
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Builder
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Table(name = "user_profile")
@JsonIgnoreProperties({"id"})
public class UserProfile {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @OneToOne
  @JoinColumn(name = "user_id")
  private UserLogin userLoginId;

  @Column(name = "introduction", length = 200)
  private String introduction;

  @Column(name = "image_url", length = 200)
  private String imageUrl;

  @CreatedDate
  @Column(name = "join_date", nullable = false)
  private Date joinDate;

  @UpdateTimestamp
  @Column(name = "update_date", nullable = false)
  private Date updateDate;

}
