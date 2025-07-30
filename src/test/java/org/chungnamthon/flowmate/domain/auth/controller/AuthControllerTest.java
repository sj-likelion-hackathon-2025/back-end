package org.chungnamthon.flowmate.domain.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.chungnamthon.flowmate.ControllerTestSupport;
import org.chungnamthon.flowmate.domain.auth.service.dto.ReissueTokenServiceRequest;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

class AuthControllerTest extends ControllerTestSupport {

    @DisplayName("토큰을 재발급한다.")
    @Test
    void reissue() throws JsonProcessingException {
        var tokenResponse = new TokenResponse("new-AT", "new-RT");
        given(authService.reissue(any())).willReturn(tokenResponse);

        var request = new ReissueTokenServiceRequest("refresh-token");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/auth/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .exchange();

        assertThat(result)
                .hasStatusOk()
                .bodyJson()
                .hasPathSatisfying("$.accessToken", v -> v.assertThat().isEqualTo(tokenResponse.accessToken()))
                .hasPathSatisfying("$.refreshToken", v -> v.assertThat().isEqualTo(tokenResponse.refreshToken()));

        verify(authService).reissue(request);
    }

    @DisplayName("유효하지 않은 값 요청으로 재발급 실패")
    @Test
    void failReissue() throws JsonProcessingException {
        var tokenResponse = new TokenResponse("new-AT", "new-RT");
        given(authService.reissue(any())).willReturn(tokenResponse);

        var request = new ReissueTokenServiceRequest("");
        String requestJson = objectMapper.writeValueAsString(request);

        MvcTestResult result = mvcTester.post().uri("/auth/reissue")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .exchange();

        assertThat(result).hasStatus(BAD_REQUEST);
    }

}