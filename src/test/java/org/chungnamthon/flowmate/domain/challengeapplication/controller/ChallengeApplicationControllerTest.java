package org.chungnamthon.flowmate.domain.challengeapplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.chungnamthon.flowmate.ControllerTestSupport;
import org.chungnamthon.flowmate.domain.challengeapplication.controller.dto.ChallengeApplicationApproveRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.controller.dto.ChallengeApplicationCreateRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ApplicationStatus;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationApproveServiceRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

class ChallengeApplicationControllerTest extends ControllerTestSupport {

    @DisplayName("챌린지 신청을 한다")
    @Test
    void apply() throws JsonProcessingException {
        var message = "20글자가 넘는 유효한 신청 메세지입니다.";
        var challengeId = 1L;
        var request = new ChallengeApplicationCreateRequest(message);
        String requestJson = objectMapper.writeValueAsString(request);

        assertThat(
                mvcTester.post().uri("/challenges/{challengeId}/applications", challengeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .hasStatusOk()
                .bodyJson();

        verify(challengeApplicationCommandService).apply(any(ChallengeApplicationCreateServiceRequest.class));
    }

    @DisplayName("신청 메세지가 20글자 미만이므로 유효성 검증 실패로 예외를 반환한다.")
    @Test
    void failApply() throws JsonProcessingException {
        var message = "invalid message.";
        var challengeId = 1L;
        var request = new ChallengeApplicationCreateRequest(message);
        String requestJson = objectMapper.writeValueAsString(request);
        assertThat(
                mvcTester.post().uri("/challenges/{challengeId}/applications", challengeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .hasStatus(HttpStatus.BAD_REQUEST)
                .bodyJson();
    }

    @DisplayName("리더가 챌린지 신청 승인 결과를 보낸다.")
    @Test
    void approve() throws JsonProcessingException {
        var challengeId = 1L;
        var applicationId = 1L;
        var request = new ChallengeApplicationApproveRequest(ApplicationStatus.APPROVED.name());
        String requestJson = objectMapper.writeValueAsString(request);
        doNothing().when(challengeApplicationCommandService).approve(any(ChallengeApplicationApproveServiceRequest.class));

        assertThat(
                mvcTester.post().uri("/challenges/{challengeId}/applications/{applicationId}", challengeId, applicationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .hasStatus(HttpStatus.OK)
                .bodyJson();
    }

}