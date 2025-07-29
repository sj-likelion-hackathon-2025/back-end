package org.chungnamthon.flowmate.domain.auth.controller.dto;

import jakarta.validation.constraints.NotBlank;
import org.chungnamthon.flowmate.domain.auth.service.dto.ReissueTokenServiceRequest;

public record ReissueTokenRequest(
        @NotBlank(message = "refreshToken은 필수 값입니다.")
        String refreshToken
) {

    public ReissueTokenServiceRequest toServiceRequest() {
        return new ReissueTokenServiceRequest(refreshToken);
    }

}