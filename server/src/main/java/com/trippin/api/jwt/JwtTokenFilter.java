package com.trippin.api.jwt;

import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.repository.MemoryTokenRepository;
import com.trippin.api.user.repository.UserLoginRepository;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

// OncePerRequestFilter : 매번 들어갈 때 마다 체크 해주는 필터
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final UserLoginRepository userLoginRepository;
  private final String secretKey;
  private final MemoryTokenRepository memoryTokenRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    // Header의 Authorization 값이 비어있으면 => Jwt Token을 전송하지 않음 => 로그인 하지 않음
    if (authorizationHeader == null) {
      filterChain.doFilter(request, response);
      return;
    }

    // Header의 Authorization 값이 'Bearer '로 시작하지 않으면 => 잘못된 토큰
    if (!authorizationHeader.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    // 전송받은 값에서 'Bearer ' 뒷부분(Jwt Token) 추출
    String token = authorizationHeader.split(" ")[1];

    // 전송받은 Jwt Token이 만료되었으면 => 다음 필터 진행(인증 X)
    if (JwtTokenUtil.isExpired(token, secretKey)) {
      // 등록된 유저 토큰 정보 삭제
      UserLogin userLogin = memoryTokenRepository.getUserLoginByToken(token);
      memoryTokenRepository.delete(userLogin);

      filterChain.doFilter(request, response);
      return;
    }

    // Jwt Token에서 loginId 추출
    String loginId = JwtTokenUtil.getLoginId(token, secretKey);

    // 추출한 loginId로 User 찾아오기
    UserLogin loginUser = userLoginRepository.findByUsername(loginId);

    // loginUser 정보로 UsernamePasswordAuthenticationToken 발급
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
        loginUser.getUsername(), null);
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

    // 권한 부여
    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    filterChain.doFilter(request, response);
  }
}
