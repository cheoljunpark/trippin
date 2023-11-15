package com.trippin.api.user.service;

import com.trippin.api.exception.BaseException;
import com.trippin.api.exception.ErrorCode;
import com.trippin.api.jwt.JwtTokenUtil;
import com.trippin.api.user.domain.AuthPassword;
import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.dto.JoinDto;
import com.trippin.api.user.dto.LoginDto;
import com.trippin.api.user.repository.AuthPasswordRepository;
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

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access-token.expiretime}")
  private long expireTime;     // Token 유효 시간 = 60분

  public UserLogin join(JoinDto joinDto) {
    // dto -> entity
    UserLogin user = joinDto.toEntity();

    // 비밀번호와 비밀번호확인 일치 판단
    if (!joinDto.getPassword().equals(joinDto.getPasswordConfirm())) {
      throw new BaseException(ErrorCode.PASSWORD_NOT_MATCH);
    }

    // 아이디나 이메일 중복 확인
    if (userLoginRepository.existsByEmailOrUserName(joinDto.getEmail(), joinDto.getUserName())) {
      throw new BaseException(ErrorCode.DUPLICATED_EMAIL);
    }

    // 유저 저장
    userLoginRepository.save(user);
    System.out.println("회원가입 성공");

    // entity에서 pk 가져오기
    Long userId = user.getId();

    AuthPassword authPassword = AuthPassword.builder()
        .userId(userId)
        .password(joinDto.getPassword())
        .build();

    // 유저의 비밀번호 암호화
    authPassword.hashPassword(bCryptPasswordEncoder);

    // 비밀번호 저장
    authPasswordRepository.save(authPassword);

    // 생성된 유저 반환
    return user;
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
      String jwtToken = JwtTokenUtil.createToken(userLogin.getUserName(), secretKey,
          expireTime);
      userLogin.setToken(jwtToken);
      return userLogin;
    } else {  // 회원정보는 있지만 비밀번호가 틀림
      System.out.println("비밀번호 틀림");
      throw new BaseException(ErrorCode.LOGIN_FAIL);
    }

  }
}
