package org.chungnamthon.flowmate.domain.challengeapplication.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;

public record ChallengeApplicationCreateRequest(
        @NotBlank(message = "신청 각오를 입력해주세요.")
        @Size(min = 20, message = "20 글자 이상 입력해주세요.")
        String message
) {

    public ChallengeApplicationCreateServiceRequest toServiceRequest(Long memberId, Long challengeId) {
        return ChallengeApplicationCreateServiceRequest.builder()
                .message(message)
                .memberId(memberId)
                .challengeId(challengeId)
                .build();
    }

}