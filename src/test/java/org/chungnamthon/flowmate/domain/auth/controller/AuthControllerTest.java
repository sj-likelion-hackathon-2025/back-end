package org.chungnamthon.flowmate.domain.auth.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.auth.service.AuthService;
import org.chungnamthon.flowmate.domain.auth.service.dto.ReissueTokenServiceRequest;
import org.chungnamthon.flowmate.global.security.TestSecurityConfig;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.assertj.MvcTestResult;

@RequiredArgsConstructor
@Import(TestSecurityConfig.class)
@WebMvcTest(AuthController.class)
class AuthControllerTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @MockitoBean
    private AuthService authService;

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