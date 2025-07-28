package org.chungnamthon.flowmate.domain.member.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class MemberRepository {

    private final MemberJpaRepository memberJpaRepository;

    public void save(Member member) {
        memberJpaRepository.save(member);
    }

    public Member findById(Long id) {
        return memberJpaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_MEMBER));
    }

    public Optional<Member> findBySocialId(String socialId) {
        return memberJpaRepository.findBySocialId(socialId);
    }

    public boolean existsByName(String name) {
        return memberJpaRepository.existsByName(name);
    }

    public Member findByRefreshToken(String refreshToken) {
        return memberJpaRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new NotFoundException(ErrorStatus.NOT_FOUND_TOKEN));
    }

}