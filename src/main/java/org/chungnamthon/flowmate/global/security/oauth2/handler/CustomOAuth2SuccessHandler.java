package org.chungnamthon.flowmate.global.security.oauth2.handler;

import static org.chungnamthon.flowmate.domain.member.entity.Role.ROLE_GUEST;
import static org.chungnamthon.flowmate.global.security.jwt.common.TokenType.ACCESS;
import static org.chungnamthon.flowmate.global.security.jwt.common.TokenType.REFRESH;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chungnamthon.flowmate.domain.member.entity.Role;
import org.chungnamthon.flowmate.domain.member.service.MemberCommandService;
import org.chungnamthon.flowmate.global.security.jwt.JwtProvider;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.chungnamthon.flowmate.global.security.oauth2.dto.CustomOAuth2Member;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final MemberCommandService memberCommandService;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();
        TokenResponse tokenResponse = jwtProvider.createTokens(oAuth2Member.getMemberId(), oAuth2Member.getRole());

        memberCommandService.updateRefreshToken(oAuth2Member.getMemberId(), tokenResponse.refreshToken());
        log.info("소셜 로그인 성공: {} ", oAuth2Member.getName());

        String redirectUrl = createRedirectUrl(tokenResponse, oAuth2Member.getRole());

        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private String createRedirectUrl(TokenResponse tokenResponse, Role role) {
        String path = role.equals(ROLE_GUEST) ? "/members/profile" : "/";

        return UriComponentsBuilder.fromUriString("http://localhost:5173" + path)
                .queryParam(ACCESS.getValue(), tokenResponse.accessToken())
                .queryParam(REFRESH.getValue(), tokenResponse.refreshToken())
                .build()
                .toUriString();
    }

}