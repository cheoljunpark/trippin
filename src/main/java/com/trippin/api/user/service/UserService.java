package com.trippin.api.user.service;

import com.trippin.api.achievement.domain.Achievement;
import com.trippin.api.achievement.repository.AchievementRepository;
import com.trippin.api.course.domain.Course;
import com.trippin.api.course.domain.Spot;
import com.trippin.api.course.repository.CourseRepository;
import com.trippin.api.course.repository.SpotRepository;
import com.trippin.api.exception.BaseException;
import com.trippin.api.exception.ErrorCode;
import com.trippin.api.user.domain.AuthPassword;
import com.trippin.api.user.domain.UserAchievement;
import com.trippin.api.user.domain.UserCourse;
import com.trippin.api.user.domain.UserCourseSpot;
import com.trippin.api.user.domain.UserCourseSpotArticle;
import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.domain.UserPrivacy;
import com.trippin.api.user.domain.UserProfile;
import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.UserAchievementDto;
import com.trippin.api.user.dto.UserCourseDto;
import com.trippin.api.user.dto.UserCourseSpotArticleDto;
import com.trippin.api.user.dto.UserCourseSpotDto;
import com.trippin.api.user.dto.UserPrivacyDto;
import com.trippin.api.user.dto.UserProfileDto;
import com.trippin.api.user.repository.AuthPasswordRepository;
import com.trippin.api.user.repository.MemoryTokenRepository;
import com.trippin.api.user.repository.UserAchievementRepository;
import com.trippin.api.user.repository.UserCourseRepository;
import com.trippin.api.user.repository.UserCourseSpotArticleRepository;
import com.trippin.api.user.repository.UserCourseSpotRepository;
import com.trippin.api.user.repository.UserLoginRepository;
import com.trippin.api.user.repository.UserPrivacyRepository;
import com.trippin.api.user.repository.UserProfileRepository;
import java.util.List;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

  private final UserLoginRepository userLoginRepository;
  private final AuthPasswordRepository authPasswordRepository;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final UserPrivacyRepository userPrivacyRepository;
  private final UserProfileRepository userProfileRepository;
  private final UserAchievementRepository userAchievementRepository;
  private final AchievementRepository achievementRepository;
  private final UserCourseRepository userCourseRepository;
  private final CourseRepository courseRepository;
  private final SpotRepository spotRepository;
  private final UserCourseSpotRepository userCourseSpotRepository;
  private final UserCourseSpotArticleRepository userCourseSpotArticleRepository;

  private final MemoryTokenRepository memoryTokenRepository;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access-token.expiretime}")
  private long expireTime;  // Token 유효 시간 = 60분

  public UserLogin join(JoinDto joinDto) {
    // dto -> entity
    UserLogin userLogin = joinDto.toEntity();

    // 아이디나 이메일 중복 확인
    if (userLoginRepository.existsByEmailOrUsername(joinDto.getEmail(), joinDto.getUsername())) {
      throw new BaseException(ErrorCode.DUPLICATED_EMAIL);
    }

    // 유저 저장
    userLoginRepository.save(userLogin);
    System.out.println("회원가입 성공");

    // entity에서 pk 가져오기
    Long userId = userLogin.getId();

    AuthPassword authPassword = AuthPassword.builder()
        .userLoginId(userLogin)
        .password(joinDto.getPassword())
        .build();

    // 유저의 비밀번호 암호화
    authPassword.hashPassword(bCryptPasswordEncoder);

    // 비밀번호 저장
    authPasswordRepository.save(authPassword);

    // 생성된 유저 반환
    return userLogin;
  }


  public void delete(String userName) {
    System.out.println("삭제");
    userLoginRepository.deleteByUsername(userName);
  }

  public Object getUserinfo(String username) {
    return null;
  }

  public List<?> getAllUsers() {
    System.out.println("전체 리스트 조회");
    List<UserLogin> users = userLoginRepository.findAll();
    return users;
  }

  public Object postPrivacy(String username, UserPrivacyDto userPrivacyDto) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    UserPrivacy userPrivacy = userPrivacyDto.toEntity(userLoginId);
    return userPrivacyRepository.save(userPrivacy);
  }

  public Object getPrivacy(String username) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    return userPrivacyRepository.findByUserLoginId(userLoginId);
  }

  public Object postProfile(String username, UserProfileDto userProfileDto) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    UserProfile userProfile = userProfileDto.toEntity(userLoginId);
    return userProfileRepository.save(userProfile);
  }

  public Object putProfile(String username, UserProfileDto userProfileDto) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    UserProfile userProfile = userProfileDto.toEntity(userLoginId);
    return userProfile;
  }

  public Object getProfile(String username) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    return userProfileRepository.findByUserLoginId(userLoginId);
  }

  public Object postCourse(String username, UserCourseDto userCourseDto) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    Long courseId = userCourseDto.getCourseId();

    Course course = courseRepository.findById(courseId).get();

    UserCourse userCourse = userCourseDto.toEntity(userLoginId, course);
    return userCourseRepository.save(userCourse);
  }

  public Object postAchievement(String username, UserAchievementDto userAchievementDto) {
    UserLogin userLoginId = userLoginRepository.findByUsername(username);
    Achievement achievementId = achievementRepository.findById(
            userAchievementDto.getAchievementId())
        .get();
    UserAchievement userAchievement = userAchievementDto.toEntity(achievementId, userLoginId);

    return userAchievementRepository.save(userAchievement);
  }

  public Object postUserCourseSpot(String username, UserCourseSpotDto userCourseSpotDto) {
    UserCourse userCourseId = userCourseRepository.findById(userCourseSpotDto.getUserCourseId())
        .get();
    Spot spotId = spotRepository.findById(userCourseSpotDto.getSpotId()).get();

    UserCourseSpot userCourseSpot = userCourseSpotDto.toEntity(userCourseId, spotId);

    return userCourseSpotRepository.save(userCourseSpot);
  }

  public Object postUserCourseSpotArticle(String username,
      UserCourseSpotArticleDto userCourseSpotArticleDto) {
    UserCourseSpot userCourseSpot = userCourseSpotRepository.findById(
            userCourseSpotArticleDto.getUserCourseSpotId())
        .get();

    UserCourseSpotArticle userCourseSpotArticle = userCourseSpotArticleDto.toEntity(userCourseSpot);

    return userCourseSpotArticleRepository.save(userCourseSpotArticle);
  }

}
