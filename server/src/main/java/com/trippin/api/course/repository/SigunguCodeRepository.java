package com.trippin.api.course.repository;

import com.trippin.api.course.domain.SigunguCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SigunguCodeRepository extends JpaRepository<SigunguCode, Long> {

  SigunguCode findTopByCode(String sigungucode);

}
