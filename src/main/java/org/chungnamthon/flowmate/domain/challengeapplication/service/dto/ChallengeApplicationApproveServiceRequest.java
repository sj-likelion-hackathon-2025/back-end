package org.chungnamthon.flowmate.domain.challengeapplication.service.dto;

import lombok.Builder;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ApplicationStatus;

@Builder
public record ChallengeApplicationApproveServiceRequest(
        ApplicationStatus status,
        Long leaderId,
        Long challengeId,
        Long applicationId
) {

}