package org.chungnamthon.flowmate.domain.challengeapplication.controller.dto;

import org.chungnamthon.flowmate.domain.challengeapplication.entity.ApplicationStatus;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationApproveServiceRequest;
import org.chungnamthon.flowmate.global.annotation.EnumValid;

public record ChallengeApplicationApproveRequest(
        @EnumValid(enumClass = ApplicationStatus.class, message = "유효하지 않은 형식입니다.")
        String status
) {

    public ChallengeApplicationApproveServiceRequest toServiceRequest(Long leaderId, Long challengeId, Long applicationId) {
        return ChallengeApplicationApproveServiceRequest.builder()
                .status(ApplicationStatus.valueOf(status))
                .leaderId(leaderId)
                .challengeId(challengeId)
                .applicationId(applicationId)
                .build();
    }

}