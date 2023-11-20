package com.trippin.api.user.service;

import com.trippin.api.exception.BaseException;
import com.trippin.api.exception.ErrorCode;
import com.trippin.api.jwt.JwtTokenUtil;
import com.trippin.api.user.domain.AuthPassword;
import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.LoginDto;
import com.trippin.api.user.repository.AuthPasswordRepository;
import com.trippin.api.user.repository.MemoryTokenRepository;
import com.trippin.api.user.repository.UserLoginRepository;
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
  //  private final UserFollowRepository userFollowRepository;
//  private final UserPrivacyRepository userPrivacyRepository;
//  private final UserProfileRepository userProfileRepository;
//  private final UserAchievementRepository userAchievementRepository;
  private final MemoryTokenRepository memoryTokenRepository;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access-token.expiretime}")
  private long expireTime;  // Token 유효 시간 = 60분

  public UserLogin join(JoinDto joinDto) {
    // dto -> entity
    UserLogin userLogin = joinDto.toEntity();

    // 아이디나 이메일 중복 확인
    if (userLoginRepository.existsByEmailOrUserName(joinDto.getEmail(), joinDto.getUserName())) {
      throw new BaseException(ErrorCode.DUPLICATED_EMAIL);
    }

    // 유저 저장
    userLoginRepository.save(userLogin);
    System.out.println("회원가입 성공");

    // entity에서 pk 가져오기
    Long userId = userLogin.getId();

    AuthPassword authPassword = AuthPassword.builder()
        .userId(userLogin)
        .password(joinDto.getPassword())
        .build();

    // 유저의 비밀번호 암호화
    authPassword.hashPassword(bCryptPasswordEncoder);

    // 비밀번호 저장
    authPasswordRepository.save(authPassword);

    // 생성된 유저 반환
    return userLogin;
  }

  public UserLogin login(LoginDto loginDto) {
    // 유저가 있으면 entity에 담기
    UserLogin userLogin = userLoginRepository.findByEmailOrUserName(loginDto.getEmail(),
        loginDto.getUserName());

    // 아이디나 이메일이 일치하는 유저가 없을 때
    if (!userLoginRepository.existsByUserName(loginDto.getUserName())
        && !userLoginRepository.existsByEmail(loginDto.getEmail())) {
      System.out.println("회원정보가 없음");
      throw new BaseException(ErrorCode.MEMBER_NOT_FOUND);
    }

    // 아이디나 이메일이 일치하는 유저가 있을 때
    Long userId = userLogin.getId();  // 아이디를 가져와서
    AuthPassword ap = authPasswordRepository.findByUserId(
        userId); // 아이디와 일치하는 row를 가져옴

    if (ap.checkPassword(loginDto.getPassword(), bCryptPasswordEncoder)) {
      System.out.println("로그인 성공");

      // 로그인 성공 => Jwt Token 발급
      String jwtToken = JwtTokenUtil.createToken(userLogin, secretKey,
          expireTime);
//      userLogin.setToken(jwtToken);
      memoryTokenRepository.save(userLogin, jwtToken);
      return userLogin;
    } else {  // 회원정보는 있지만 비밀번호가 틀림
      System.out.println("비밀번호 틀림");
      throw new BaseException(ErrorCode.LOGIN_FAIL);
    }

  }

  public void logout(String username) {
    UserLogin userLogin = userLoginRepository.findByUserName(username);
    memoryTokenRepository.delete(userLogin);
    System.out.println("로그아웃 성공");
  }

  public void delete(String userName) {
    System.out.println("삭제");
    userLoginRepository.deleteByUserName(userName);
  }

//  public Object getUserinfo(String username) {
//    return null;
//  }
//
//  public List<?> getAllUsers() {
//    System.out.println("전체 리스트 조회");
//    List<UserLogin> users = userLoginRepository.findAll();
//    return users;
//  }
//
//  public Integer getFollowing(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    System.out.println("follwing: " + userFollowRepository.findById(id).get().getFollowee());
//    return userFollowRepository.findById(id).get().getFollowee();
//  }

//  public Integer getFollower(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    System.out.println("follwer: " + userFollowRepository.findById(id).get().getFollower());
//    return userFollowRepository.findById(id).get().getFollower();
//  }

//  public Object putPrivacy(String username, UserPrivacyDto userPrivacyDto) {
//    Long userId = userLoginRepository.findByUserName(username).getId();
//    UserPrivacy userPrivacy = userPrivacyDto.toEntity(userId);
//
//    // UserLogin에도 개인정보 저장
//    userLoginRepository.findByUserName(username).setUserPrivacy(userPrivacy);
//
//    return userPrivacyRepository.save(userPrivacy);
////    return null;
//  }
//
//  public Object getPrivacy(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    return userPrivacyRepository.findById(id);
//  }
//
//  public Object putProfile(String username, UserProfileDto userProfileDto) {
//    // TODO: 노완벽
//    Long userId = userLoginRepository.findByUserName(username).getId();
//
//    Date joinDate = new Date();
//    Date updateDate = new Date();
//
//    UserProfile userProfile = userProfileDto.toEntity(userId, joinDate, updateDate);
//
//    // UserLogin에도 프로필 저장
//    userLoginRepository.findByUserName(username).setUserProfile(userProfile);
//
//    return userProfileRepository.save(userProfile);
//  }
//
//  public Object getProfile(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    return userProfileRepository.findById(id);
//  }
//
//  public Object postFollowing(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    int followee = userFollowRepository.findById(id).get().getFollowee();
//    int follower = userFollowRepository.findById(id).get().getFollower();
//    return userFollowRepository.save(new UserFollow(id, follower, ++followee));
//  }
//
//  public Object postFollower(String username) {
//    Long id = userLoginRepository.findByUserName(username).getId();
//    int followee = userFollowRepository.findById(id).get().getFollowee();
//    int follower = userFollowRepository.findById(id).get().getFollower();
//    return userFollowRepository.save(new UserFollow(id, ++follower, followee));
//  }
//
//  public Object postAchievement(String username, UserAchievementDto userAchievementDto) {
//    UserLogin userLogin = userLoginRepository.findByUserName(username);
//    UserAchievement userAchievement = userAchievementDto.toEntity(userLogin);
//
//    userAchievement.setUserLogin(userLogin);
//
//    return userAchievementRepository.save(userAchievement);
//  }
}
