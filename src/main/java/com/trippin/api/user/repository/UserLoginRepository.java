package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

  UserLogin findByEmail(String input);

  UserLogin findByUserName(String userName);

  Boolean existsByUsername(String userName);

  Boolean existsByEmail(String email);

  Boolean existsByEmailOrUserName(String email, String userName);

  void deleteByUserName(String userName);
}
