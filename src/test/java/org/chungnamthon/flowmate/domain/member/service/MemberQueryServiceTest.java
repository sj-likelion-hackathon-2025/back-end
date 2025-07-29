package org.chungnamthon.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberProfileResponse;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
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

    @DisplayName("프로필 초기 등록 시 필요한 회원 정보를 가져온다.")
    @Test
    void getProfile() {
        var member = MemberFixture.createMember("kwak");
        member.updateProfileImgUrl("test-image-url");
        memberRepository.save(member);

        MemberProfileResponse response = memberQueryService.getProfile(member.getId());

        assertThat(response)
                .extracting(
                        MemberProfileResponse::name,
                        MemberProfileResponse::image,
                        MemberProfileResponse::grade,
                        MemberProfileResponse::point
                ).containsExactly(
                        member.getName(),
                        member.getProfileImgUrl(),
                        member.getGrade(),
                        member.getPoint()
                );
    }

    @DisplayName("회원이 존재하지 않는다.")
    @Test
    void failGetProfile() {
        var member = MemberFixture.createMember("kwak");
        member.updateProfileImgUrl("test-image-url");
        memberRepository.save(member);

        assertThatThrownBy(() -> memberQueryService.getProfile(99999L))
                .isInstanceOf(NotFoundException.class);
    }


}