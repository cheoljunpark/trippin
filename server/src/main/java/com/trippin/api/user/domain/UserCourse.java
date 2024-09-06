package com.trippin.api.user.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.trippin.api.course.domain.Course;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
@Table(name = "user_course")
public class UserCourse {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "user_id")
  private UserLogin userLoginId;

  @OneToOne
  @JoinColumn(name = "course_id")
  private Course courseId;

  @CreatedDate
  @Temporal(TemporalType.DATE)
  @Column(name = "start_date", nullable = false)
  private Date startDate;

  @UpdateTimestamp
  @Temporal(TemporalType.DATE)
  @Column(name = "complete_date")
  private Date completeDate;
}
