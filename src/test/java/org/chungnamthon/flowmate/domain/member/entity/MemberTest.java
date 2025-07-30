package org.chungnamthon.flowmate.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberTest {

    Member member;

    @BeforeEach
    void setUp() {
        member = MemberFixture.createMember();
    }

    @DisplayName("멤버를 생성한다")
    @Test
    void createMember() {
        assertThat(member).isNotNull();
    }

    @DisplayName("멤버 포인트를 적립하고 등급 상태를 확인한다.")
    @Test
    void updatePoint() {
        assertThat(member.getPoint()).isZero();
        assertThat(member.getGrade()).isEqualTo(Grade.ROOKIE);
        member.updatePoint(1000L);

        assertThat(member.getPoint()).isEqualTo(1000L);
        // 1000 Point 부터 Pro 등급이다
        assertThat(member.getGrade()).isEqualTo(Grade.PRO);
        // 등급 강등
        member.updatePoint(-100L);
        assertThat(member)
                .extracting(Member::getPoint, Member::getGrade)
                .containsExactly(       900L,     Grade.JUNIOR);
    }

    @DisplayName("RT를 변경한다.")
    @Test
    void updateRefreshToken() {
        var testRefreshToken = "testRefreshToken";

        member.updateRefreshToken(testRefreshToken);

        assertThat(member.getRefreshToken()).isEqualTo(testRefreshToken);
    }

    @DisplayName("회원 이메일을 변경한다.")
    @Test
    void updateEmail() {
        var testEmail = "testEmail";

        member.updateEmail(testEmail);

        assertThat(member.getEmail()).isEqualTo(testEmail);
    }

    @DisplayName("회원 이름을 변경한다.")
    @Test
    void updateName() {
        var testName = "testName";

        member.updateName(testName);

        assertThat(member.getName()).isEqualTo(testName);
    }

    @DisplayName("회원 프로필 사진을 변경한다.")
    @Test
    void updateProfileImgUrl() {
        var testProfileImgUrl = "testProfileImgUrl";

        member.updateProfileImgUrl(testProfileImgUrl);

        assertThat(member.getProfileImgUrl()).isEqualTo(testProfileImgUrl);
    }

    @DisplayName("회원 권한을 게스트에서 멤버 권한으로 업그레이드한다")
    @Test
    void upgradeRoleToMember() {
        assertThat(member.getRole()).isEqualTo(Role.ROLE_GUEST);

        member.upgradeRoleToMember();

        assertThat(member.getRole()).isEqualTo(Role.ROLE_MEMBER);
    }

}