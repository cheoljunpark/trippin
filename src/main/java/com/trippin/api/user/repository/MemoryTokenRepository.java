package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserLogin;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class MemoryTokenRepository {

  private final Map<UserLogin, String> tokens;

  public MemoryTokenRepository() {
    this.tokens = new HashMap<>();
  }

  public void save(UserLogin userLogin, String token) {
    tokens.put(userLogin, token);
  }

  // TODO: null 처리
  public String read(UserLogin userLogin) {
    return tokens.get(userLogin);
  }

  public void delete(UserLogin userLogin) {
    tokens.remove(userLogin);
  }

  public UserLogin getUserLoginByToken(String token) {
    return tokens.entrySet().stream()
        .filter(entry -> entry.getValue().equals(token))
        .findFirst()
        .get()
        .getKey();
  }


}
