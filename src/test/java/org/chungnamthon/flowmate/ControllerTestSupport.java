package org.chungnamthon.flowmate;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.chungnamthon.flowmate.domain.auth.controller.AuthController;
import org.chungnamthon.flowmate.domain.auth.service.AuthService;
import org.chungnamthon.flowmate.domain.challenge.controller.ChallengeController;
import org.chungnamthon.flowmate.domain.challenge.service.ChallengeCommandService;
import org.chungnamthon.flowmate.domain.challengeapplication.controller.ChallengeApplicationController;
import org.chungnamthon.flowmate.domain.challengeapplication.service.ChallengeApplicationCommandService;
import org.chungnamthon.flowmate.global.security.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@Import(TestSecurityConfig.class)
@WebMvcTest(
        controllers = {
                AuthController.class,
                ChallengeController.class,
                ChallengeApplicationController.class
        })
public abstract class ControllerTestSupport {

    @Autowired
    protected MockMvcTester mvcTester;
    @Autowired
    protected ObjectMapper objectMapper;

    @MockitoBean
    protected AuthService authService;
    @MockitoBean
    protected ChallengeCommandService challengeCommandService;
    @MockitoBean
    protected ChallengeApplicationCommandService challengeApplicationCommandService;

}