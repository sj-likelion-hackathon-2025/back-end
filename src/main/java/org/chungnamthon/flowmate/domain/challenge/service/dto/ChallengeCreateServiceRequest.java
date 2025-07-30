package org.chungnamthon.flowmate.domain.challenge.service.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.chungnamthon.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.chungnamthon.flowmate.domain.member.entity.Category;

@Builder
public record ChallengeCreateServiceRequest(
        String title,
        String introduction,
        Category category,
        LocalDate startDate,
        LocalDate endDate,
        String rule,
        Long maxParticipants,
        Long memberId
) {

    public ChallengeCreateDomainRequest toDomainRequest() {
        return ChallengeCreateDomainRequest.builder()
                .title(title)
                .introduction(introduction)
                .category(category)
                .startDate(startDate)
                .endDate(endDate)
                .rule(rule)
                .maxParticipants(maxParticipants)
                .build();
    }

}