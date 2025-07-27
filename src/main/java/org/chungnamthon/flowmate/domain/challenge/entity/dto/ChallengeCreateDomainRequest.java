package org.chungnamthon.flowmate.domain.challenge.entity.dto;

import java.time.LocalDate;
import lombok.Builder;
import org.chungnamthon.flowmate.domain.member.entity.Category;

@Builder
public record ChallengeCreateDomainRequest(
        String title,
        String introduction,
        Category category,
        LocalDate startDate,
        LocalDate endDate,
        Long maxParticipants,
        String rule
) {

}