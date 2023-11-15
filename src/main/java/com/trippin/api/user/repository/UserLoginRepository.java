package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, Long> {

  UserLogin findByEmailOrUserName(String email, String userName);

  UserLogin findByUserName(String loginId);

  Boolean existsByUserName(String userName);

  Boolean existsByEmail(String email);

  Boolean existsByEmailOrUserName(String email, String userName);
}
