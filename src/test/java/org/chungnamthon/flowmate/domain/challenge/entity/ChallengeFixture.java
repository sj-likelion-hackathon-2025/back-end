package org.chungnamthon.flowmate.domain.challenge.entity;

import java.time.LocalDate;
import org.chungnamthon.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.chungnamthon.flowmate.domain.challenge.service.dto.ChallengeCreateServiceRequest;
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
    public static ChallengeCreateServiceRequest getChallengeCreateServiceRequest(Long memberId) {
        return ChallengeCreateServiceRequest.builder()
                .title("아침 6시 기상 챌린지")
                .introduction("매일 아침 6시에 일어나기 도전!")
                .category(Category.DIET) // enum 타입으로 입력
                .startDate(LocalDate.of(2024, 6, 1))
                .endDate(LocalDate.of(2024, 6, 30))
                .rule("지정된 시간에 기상 인증 사진 업로드")
                .maxParticipants(5L)
                .memberId(memberId) // 챌린지 생성자 ID
                .build();
    }

    public static ChallengeCreateServiceRequest getChallengeCreateServiceRequest(LocalDate startDate, LocalDate endDate) {
        return ChallengeCreateServiceRequest.builder()
                .title("아침 6시 기상 챌린지")
                .introduction("매일 아침 6시에 일어나기 도전!")
                .category(Category.DIET) // enum 타입으로 입력
                .startDate(startDate)
                .endDate(endDate)
                .rule("지정된 시간에 기상 인증 사진 업로드")
                .maxParticipants(5L)
                .memberId(1L) // 챌린지 생성자 ID
                .build();
    }

}