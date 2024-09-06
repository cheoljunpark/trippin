package com.trippin.api.achievement.service;

import com.trippin.api.achievement.domain.Achievement;
import com.trippin.api.achievement.domain.AchievementType;
import com.trippin.api.achievement.dto.AchievementDto;
import com.trippin.api.achievement.dto.AchievementTypeDto;
import com.trippin.api.achievement.repository.AchievementRepository;
import com.trippin.api.achievement.repository.AchievementTypeRepository;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class AchievementService {

  private final AchievementRepository achievementRepository;
  private final AchievementTypeRepository achievementTypeRepository;

  public Object postAchievement(AchievementDto achievementDto) {
    AchievementType achievementType = achievementTypeRepository.findById(
        achievementDto.getAchievementType()).get();
    Achievement achievement = achievementDto.toEntity(achievementType);
    return achievementRepository.save(achievement);
  }

  public Object getAchievement(Long achievementId) {
    return achievementRepository.findById(achievementId);
  }

  public Object postAchievementType(AchievementTypeDto achievementTypeDto) {
    AchievementType achievementType = achievementTypeDto.toEntity();
    return achievementTypeRepository.save(achievementType);
  }

  public Object getAchievementType(Long achievementTypeId) {
    return achievementTypeRepository.findById(achievementTypeId);
  }
}
