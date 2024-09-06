package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserLogin;
import com.trippin.api.user.domain.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

  Object findByUserLoginId(UserLogin userLoginId);
}
