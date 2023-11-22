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
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@PropertySources({
    @PropertySource(value = { "classpath:application-jwt.yml" }, factory = YamlLoadFactory.class),
    @PropertySource(value = { "classpath:application-api.yml" }, factory = YamlLoadFactory.class)
})
@EnableWebSecurity
public class SecurityConfig {

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
    this.PERMITTED_URL = new String[] {
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
  @Bean
  protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .csrf(csrf -> csrf.disable()) // post 방식으로 값을 전송할 때 token을 사용해야 하는 보안 설정을 해제
        .sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeRequests(requests -> requests
            .antMatchers(PERMITTED_URL)
            .permitAll()
            .anyRequest().authenticated())
        .addFilterBefore(new JwtTokenFilter(userLoginRepository, secretKey, memoryTokenRepository),
            UsernamePasswordAuthenticationFilter.class)
        .build(); // JwtAuthenticationFilter를
                  // UsernamePasswordAuthenticationFilter 전에 넣는다
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();

    configuration.addAllowedOrigin("http://localhost:3000");
    configuration.addAllowedHeader("*");
    configuration.addAllowedMethod("*");
    configuration.setAllowCredentials(true);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }
}
