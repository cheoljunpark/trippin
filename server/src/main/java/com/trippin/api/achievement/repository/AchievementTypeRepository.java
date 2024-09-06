package com.trippin.api.achievement.repository;

import com.trippin.api.achievement.domain.AchievementType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementTypeRepository extends JpaRepository<AchievementType, Long> {

  AchievementType findByType(Long achievementType);
}
