package com.trippin.api.course.repository;

import com.trippin.api.course.domain.AreaCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaCodeRepository extends JpaRepository<AreaCode, Long> {

  AreaCode findByCode(String code);
}
