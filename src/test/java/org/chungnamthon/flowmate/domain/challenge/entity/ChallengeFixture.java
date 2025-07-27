package org.chungnamthon.flowmate.domain.challenge.entity;

import java.time.LocalDate;
import org.chungnamthon.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.chungnamthon.flowmate.domain.member.entity.Category;
import org.springframework.test.util.ReflectionTestUtils;

public class ChallengeFixture {

    public static Challenge createChallenge() {
        return Challenge.create(getChallengeCreateDomainRequest());
    }

    public static Challenge createChallenge(Long id) {
        Challenge challenge = Challenge.create(getChallengeCreateDomainRequest());
        ReflectionTestUtils.setField(challenge, "id", id);

        return challenge;
    }

    public static ChallengeCreateDomainRequest getChallengeCreateDomainRequest() {
        return ChallengeCreateDomainRequest.builder()
                .title("아침 러닝 챌린지")
                .introduction("매일 아침 7시에 러닝을 인증하는 챌린지입니다.")
                .category(Category.DIET)
                .startDate(LocalDate.of(2025, 8, 1))
                .endDate(LocalDate.of(2025, 8, 5))
                .maxParticipants(5L)
                .rule("지정된 시간 내 운동 인증샷을 업로드해야 합니다.")
                .build();
    }

}