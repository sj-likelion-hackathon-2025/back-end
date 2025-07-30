package org.chungnamthon.flowmate.domain.challenge.controller.dto;

import static org.chungnamthon.flowmate.global.util.TimeConverter.stringToDate;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.chungnamthon.flowmate.domain.challenge.service.dto.ChallengeCreateServiceRequest;
import org.chungnamthon.flowmate.domain.member.entity.Category;
import org.chungnamthon.flowmate.global.annotation.EnumValid;

@Builder
public record ChallengeCreateRequest(
        @NotBlank(message = "챌린지 제목은 필수입니다.")
        String title,

        @NotBlank(message = "챌린지 한줄 소개는 필수입니다.")
        @Size(max = 30, message = "챌린지 한줄 소개는 최대 30자까지 입력 가능합니다.")
        String introduction,

        @EnumValid(enumClass = Category.class, message = "유효하지 않은 카테고리입니다.")
        String category,

        @NotBlank(message = "시작일은 필수입니다.")
        String startDate,

        @NotBlank(message = "종료일은 필수입니다.")
        String endDate,

        @NotBlank(message = "인증 규칙은 필수입니다.")
        String rule,

        @Min(value = 2, message = "최소 참여자 수는 2명 이상이어야 합니다")
        @Max(value = 5, message = "최대 참여자 수는 5명 이하여야 합니다")
        Long maxParticipants
) {

    public ChallengeCreateServiceRequest toServiceRequest(Long memberId) {
        return ChallengeCreateServiceRequest.builder()
                .title(title)
                .introduction(introduction)
                .category(Category.valueOf(category))
                .startDate(stringToDate(startDate))
                .endDate(stringToDate(endDate))
                .rule(rule)
                .maxParticipants(maxParticipants)
                .memberId(memberId)
                .build();
    }

}