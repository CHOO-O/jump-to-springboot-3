package com.mysite.sbb.user;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SiteUser, Long> {
    // SiteUser.java에서 설정한 기본키의 타입은 long이다.
    Optional<SiteUser> findByUsername(String username);
}
