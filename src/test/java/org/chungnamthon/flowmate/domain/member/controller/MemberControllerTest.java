package org.chungnamthon.flowmate.domain.member.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.controller.dto.MemberCheckNameRequest;
import org.chungnamthon.flowmate.domain.member.controller.dto.MemberProfileRegisterRequest;
import org.chungnamthon.flowmate.domain.member.service.MemberCommandService;
import org.chungnamthon.flowmate.domain.member.service.MemberQueryService;
import org.chungnamthon.flowmate.global.security.TestSecurityConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@RequiredArgsConstructor
@Import(TestSecurityConfig.class)
@WebMvcTest(MemberController.class)
class MemberControllerTest {

    final MockMvcTester mvcTester;
    final ObjectMapper objectMapper;

    @MockitoBean
    MemberQueryService memberQueryService;
    @MockitoBean
    MemberCommandService memberCommandService;

    @DisplayName("회원 정보를 등록한다.")
    @Test
    void register() throws JsonProcessingException {
        var request = new MemberProfileRegisterRequest("kwakmunsu");
        var profileImage = new MockMultipartFile(
                "image",
                "profile.jpg",
                "image/jpeg",
                "profile image content".getBytes()
        );
        var requestPart = new MockMultipartFile(
                "request",
                "request.json",
                "application/json",
                objectMapper.writeValueAsString(request).getBytes()
        );

        var response = mvcTester.perform(
                        multipart("/members")
                                .file(requestPart)
                                .file(profileImage)
                                .contentType(MediaType.MULTIPART_FORM_DATA)
                                .content(objectMapper.writeValueAsString(request))
                )
                .getMvcResult()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @DisplayName("이름 중복 체크를 한다.")
    @Test
    void checkNameDuplicate() throws JsonProcessingException {
        var request = new MemberCheckNameRequest("test-name");
        doNothing().when(memberQueryService).checkNameDuplicate(any());
        var requestJson = objectMapper.writeValueAsString(request);

        var result = mvcTester.post().uri("/members/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson)
                .exchange();

        assertThat(result).hasStatusOk();
    }

}