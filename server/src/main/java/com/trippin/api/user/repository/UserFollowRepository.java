package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserFollow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollow, Long> {

}
