package com.trippin.api.user.repository;

import com.trippin.api.user.domain.UserPrivacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPrivacyRepository extends JpaRepository<UserPrivacy, Long> {

}
