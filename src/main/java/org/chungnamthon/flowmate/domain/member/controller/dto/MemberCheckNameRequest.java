package org.chungnamthon.flowmate.domain.member.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record MemberCheckNameRequest(
        @NotBlank(message = "name은 필수 값입니다.")
        String name
) {

}