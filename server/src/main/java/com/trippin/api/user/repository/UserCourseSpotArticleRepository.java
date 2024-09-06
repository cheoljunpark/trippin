package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserCourseSpotArticle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCourseSpotArticleRepository extends
    JpaRepository<UserCourseSpotArticle, Long> {

}
