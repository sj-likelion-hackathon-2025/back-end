package org.chungnamthon.flowmate.domain.challenge.controller.dto;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.chungnamthon.flowmate.domain.member.entity.Category;
import org.chungnamthon.flowmate.global.exception.BadRequestException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChallengeCreateRequestTest {

    private static Validator validator;

    @BeforeAll
    static void setup() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @DisplayName("유효한 챌린지 생성 요청 값이 전달된다.")
    @Test
    void challengeCreateRequest() {
        ChallengeCreateRequest request = ChallengeCreateRequest.builder()
                .title("챌린지 제목")
                .introduction("한줄 소개")
                .category(Category.DIET.name())
                .startDate("2024-07-01")
                .endDate("2024-07-30")
                .rule("인증 규칙")
                .maxParticipants(3L)
                .build();

        Set<ConstraintViolation<ChallengeCreateRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }

    @DisplayName("""
            유효성 검증 실패
            title - 빈 값,
            introduction - 30글자 이상,
            category - 없는 카테고리,
            rule - null
            maxParticipants - 인원수 초과
            """)
    @Test
    void failChallengeCreateRequestToInvalidRequest() {
        ChallengeCreateRequest request = ChallengeCreateRequest.builder()
                .title("")
                .introduction("InvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalidInvalid")
                .category("INVALID")
                .startDate("2024-07-01")
                .endDate("2024-07-30")
                .rule(null)
                .maxParticipants(10L)
                .build();

        Set<ConstraintViolation<ChallengeCreateRequest>> violations = validator.validate(request);

        assertThat(violations).hasSize(5);
    }

    @DisplayName("날짜 형식이 맞지 않아 serviceRequestDto로 변환할 때 예외 발생")
    @Test
    void failChallengeCreateRequestToInvalidLocalDateFormat() {
        ChallengeCreateRequest request = ChallengeCreateRequest.builder()
                .title("챌린지 제목")
                .introduction("한줄 소개")
                .category(Category.DIET.name())
                .startDate("2024.07.01")
                .endDate("2024-7-30")
                .rule("인증 규칙")
                .maxParticipants(3L)
                .build();

        assertThatThrownBy(() -> request.toServiceRequest(1L))
                .isInstanceOf(BadRequestException.class);
    }


}