package com.trippin.api.auth.service;

import com.trippin.api.exception.BaseException;
import com.trippin.api.exception.ErrorCode;
import com.trippin.api.jwt.JwtTokenUtil;
import com.trippin.api.user.domain.AuthPassword;
import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.dto.LoginDto;
import com.trippin.api.user.dto.LoginResponseDto;
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
public class AuthService {

  private final UserLoginRepository userLoginRepository;
  private final AuthPasswordRepository authPasswordRepository;
  private final PasswordEncoder bCryptPasswordEncoder;
  private final MemoryTokenRepository memoryTokenRepository;

  @Value("${jwt.secret}")
  private String secretKey;

  @Value("${jwt.access-token.expiretime}")
  private long expireTime;  // Token 유효 시간 = 60분

  public LoginResponseDto login(LoginDto loginDto) {
    String usernameOrEmail = loginDto.getUsernameOrEmail();

    // 아이디나 이메일이 일치하는 유저가 없을 때
    if (!userLoginRepository.existsByUserName(usernameOrEmail)
        && !userLoginRepository.existsByEmail(usernameOrEmail)) {
      System.out.println("회원정보가 없음");
      throw new BaseException(ErrorCode.MEMBER_NOT_FOUND);
    }

    UserLogin userLogin = userLoginRepository.findByEmail(usernameOrEmail);
    // 유저가 있으면 entity에 담기
    if (userLogin == null) {
      userLogin = userLoginRepository.findByUserName(usernameOrEmail);
    }

    // 아이디나 이메일이 일치하는 유저가 있을 때
//    Long userId = userLogin.getId();  // 아이디를 가져와서
    AuthPassword ap = authPasswordRepository.findByUserLoginId(userLogin); // 아이디와 일치하는 row를 가져옴

    if (ap.checkPassword(loginDto.getPassword(), bCryptPasswordEncoder)) {
      System.out.println("로그인 성공");

      // 로그인 성공 => Jwt Token 발급
      String jwtToken = JwtTokenUtil.createToken(userLogin, secretKey, expireTime);
//      userLogin.setToken(jwtToken);
      memoryTokenRepository.save(userLogin, jwtToken);

      return LoginResponseDto.builder().userName(userLogin.getUsername())
          .email(userLogin.getEmail()).token(memoryTokenRepository.read(userLogin)).build();


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

}
