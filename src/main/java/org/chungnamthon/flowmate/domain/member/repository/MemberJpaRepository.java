package org.chungnamthon.flowmate.domain.member.repository;

import java.util.Optional;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {

    Optional<Member> findBySocialId(String socialId);

    boolean existsByName(String name);

    Optional<Member> findByRefreshToken(String token);

}