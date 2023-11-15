package com.trippin.api.user.repository;

import com.trippin.api.user.domain.AuthPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthPasswordRepository extends JpaRepository<AuthPassword, Long> {

  AuthPassword findByUserId(Long id);
}
