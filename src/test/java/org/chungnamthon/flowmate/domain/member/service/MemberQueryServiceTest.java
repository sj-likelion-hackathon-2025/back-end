package org.chungnamthon.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record MemberQueryServiceTest(MemberQueryService memberQueryService, MemberRepository memberRepository) {

    @DisplayName("회원 이름 중복 체크")
    @Test
    void checkNameDuplicate() {
        var member = MemberFixture.createMember("kwak");
        memberRepository.save(member);

        assertThatThrownBy(() -> memberQueryService.checkNameDuplicate(member.getName()))
            .isInstanceOf(DuplicationException.class);
        // 중복이지 않다면 아무 일도 안일어난다.
        memberQueryService.checkNameDuplicate("not-exists-name");
    }

}