package com.trippin.api.config;

import com.trippin.api.jwt.JwtTokenFilter;
import com.trippin.api.user.repository.MemoryTokenRepository;
import com.trippin.api.user.repository.UserLoginRepository;
import com.trippin.api.util.YamlLoadFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Spring Security 사용을 위한 Configuration Class를 작성하기 위해서 WebSecurityConfigurerAdapter를 상속하여 클래스를
 * 생성하고
 *
 * @Configuration 애노테이션 대신 @EnableWebSecurity 애노테이션을 추가한다.
 */
@PropertySources({
    @PropertySource(value = {"classpath:application-jwt.yml"}, factory = YamlLoadFactory.class),
    @PropertySource(value = {"classpath:application-api.yml"}, factory = YamlLoadFactory.class)
})
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final UserLoginRepository userLoginRepository;
  private final MemoryTokenRepository memoryTokenRepository;
  private final String secretKey;
  private final String apiVersion;

  public SecurityConfig(UserLoginRepository userLoginRepository,
      MemoryTokenRepository memoryTokenRepository,
      @Value("${jwt.secret}") String secretKey,
      @Value("${api.version}") String apiVersion) {

    this.userLoginRepository = userLoginRepository;
    this.memoryTokenRepository = memoryTokenRepository;
    this.secretKey = secretKey;
    this.apiVersion = apiVersion;
    this.PERMITTED_URL = new String[]{
        /* swagger v2 */
        "/v2/api-docs",
        "/swagger-resources",
        "/swagger-resources/**",
        "/configuration/ui",
        "/configuration/security",
        "/swagger-ui.html",
        "/webjars/**",
        /* swagger v3 */
        "/v3/api-docs/**",
        "/swagger-ui/**",
        /* API */
        apiVersion + "/**",
    };
  }

  /**
   * PasswordEncoder를 Bean으로 등록
   */
  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  private final String[] PERMITTED_URL;

  /**
   * 인증 or 인가에 대한 설정
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .csrf().disable() // post 방식으로 값을 전송할 때 token을 사용해야하는 보안 설정을 해제
        .authorizeRequests()
        .antMatchers(PERMITTED_URL)
        .permitAll()
        .anyRequest().authenticated()
        .and()
        .addFilterBefore(new JwtTokenFilter(userLoginRepository, secretKey, memoryTokenRepository),
            UsernamePasswordAuthenticationFilter.class); // JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 전에 넣는다
    // + 토큰에 저장된 유저정보를 활용하여야 하기 때문에 CustomUserDetailService 클래스를 생성합니다.
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    http.cors();
  }
}
