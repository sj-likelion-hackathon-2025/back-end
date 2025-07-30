package org.chungnamthon.flowmate.domain.challengeapplication.service.dto;

import lombok.Builder;

@Builder
public record ChallengeApplicationCreateServiceRequest(
        String message,
        Long memberId,
        Long challengeId
) {

}
