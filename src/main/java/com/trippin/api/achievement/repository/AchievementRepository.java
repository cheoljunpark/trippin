package com.trippin.api.achievement.repository;

import com.trippin.api.achievement.domain.Achievement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {

}
