package org.chungnamthon.flowmate.domain.challenge.entity;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import org.chungnamthon.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.chungnamthon.flowmate.domain.member.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChallengeTest {

    Challenge challenge;

    ChallengeCreateDomainRequest request;

    @BeforeEach
    void setUp() {
        request = ChallengeFixture.getChallengeCreateDomainRequest();
        challenge = ChallengeFixture.createChallenge();
    }

    @DisplayName("챌린지를 생성한다.")
    @Test
    void create() {
        assertThat(challenge).isNotNull();
        assertThat(challenge.getTitle()).isEqualTo(request.title());
    }

    @DisplayName("챌린지가 시작되면 상태를 진행중으로 변경한다.")
    @Test
    void updateStatusToInProgress() {
        assertThat(challenge.getStatus()).isEqualTo(ChallengeStatus.RECRUITING);

        challenge.updateStatusToInProgress();

        assertThat(challenge.getStatus()).isEqualTo(ChallengeStatus.IN_PROGRESS);
    }

    @DisplayName("챌린지 상태가 시작 전인 경우에만 진행중 상태로 변경할 수 있다.")
    @Test
    void failUpdateStatusToInProgress() {
        challenge.updateStatusToInProgress();
        challenge.updateStatusToComplete();

        assertThatThrownBy(() -> challenge.updateStatusToInProgress())
            .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("챌린지가 종료되면 상태를 완료로 변경한다.")
    @Test
    void updateStatusToComplete() {
        challenge.updateStatusToInProgress();

        challenge.updateStatusToComplete();

        assertThat(challenge.getStatus()).isEqualTo(ChallengeStatus.COMPLETED);
    }

    @DisplayName("챌린지 상태가 진행중일 경우에 완료 상태로 변경할 수 있다.")
    @Test
    void failUpdateStatusToComplete() {
        assertThatThrownBy(() -> challenge.updateStatusToComplete())
                .isInstanceOf(IllegalStateException.class);
    }

    @DisplayName("챌린지 정보를 수정한다.")
    @Test
    void modifyChallenge() {
        var updatedTitle = "updatedTitle";
        var updatedIntroduction = "updatedIntroduction";
        var updatedCategory = Category.LIFESTYLE;
        var updateRule = "updateRule";

        challenge.modifyChallenge(updatedTitle, updatedIntroduction, updatedCategory, updateRule);

        assertThat(challenge)
                .extracting(Challenge::getTitle, Challenge::getIntroduction, Challenge::getCategory, Challenge::getRule)
                .containsExactly(updatedTitle,          updatedIntroduction,        updatedCategory,         updateRule);
    }

}