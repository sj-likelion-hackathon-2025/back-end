package org.chungnamthon.flowmate.domain.challenge.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.chungnamthon.flowmate.domain.challenge.service.ChallengeCommandService;
import org.chungnamthon.flowmate.domain.member.entity.Category;
import org.chungnamthon.flowmate.global.security.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@RequiredArgsConstructor
@Import(TestSecurityConfig.class)
@WebMvcTest(ChallengeController.class)
class ChallengeControllerTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @MockitoBean
    private ChallengeCommandService challengeCommandService;

    @DisplayName("챌린지 생성을 한다.")
    @Test
    void create() throws JsonProcessingException {
        var request = getChallengeCreateRequest();

        var requestJson = objectMapper.writeValueAsString(request);

        var result = mvcTester.post().uri("/challenges")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .exchange();

        assertThat(result).hasStatusOk().bodyJson();
    }

    private ChallengeCreateRequest getChallengeCreateRequest() {
        return ChallengeCreateRequest.builder()
                .title("아침 7시 기상 챌린지")
                .introduction("건강한 하루의 시작! 모두 7시에 기상해요")
                .category(Category.DIET.name()) // 문자열, 실제 API enum 값에 맞게
                .startDate("2024-05-01")
                .endDate("2024-05-31")
                .rule("매일 기상 인증 사진 업로드")
                .maxParticipants(5L)
                .build();
    }

}