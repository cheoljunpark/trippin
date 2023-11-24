package com.trippin.api.user.repository;

import com.trippin.api.user.domain.AuthPassword;
import com.trippin.api.user.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthPasswordRepository extends JpaRepository<AuthPassword, Long> {

  AuthPassword findByUserLoginId(UserLogin userLogin);
}
