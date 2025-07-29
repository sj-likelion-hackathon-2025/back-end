package org.chungnamthon.flowmate.domain.member.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberCreateServiceRequest;
import org.springframework.web.multipart.MultipartFile;

public record MemberProfileRegisterRequest(
        @NotBlank(message = "이름은 필수 값입니다.")
        String name
) {

    public MemberCreateServiceRequest toServiceRequest(MultipartFile image, Long memberId) {
        return MemberCreateServiceRequest.builder()
                .image(image)
                .name(name)
                .memberId(memberId)
                .build();
    }

}