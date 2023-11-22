package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserAchievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAchievementRepository extends JpaRepository<UserAchievement, Long> {

}
