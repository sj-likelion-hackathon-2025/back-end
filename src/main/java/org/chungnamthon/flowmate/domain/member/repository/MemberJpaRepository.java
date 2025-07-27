package org.chungnamthon.flowmate.domain.member.repository;

import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<Member,Long> {

}