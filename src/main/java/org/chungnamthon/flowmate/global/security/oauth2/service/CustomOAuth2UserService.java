package org.chungnamthon.flowmate.global.security.oauth2.service;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.security.oauth2.dto.CustomOAuth2Member;
import org.chungnamthon.flowmate.global.security.oauth2.userinfo.KakaoOAuth2UserInfo;
import org.chungnamthon.flowmate.global.security.oauth2.userinfo.OAuth2UserInfo;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        try {
            return processOAuth2User(oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    private OAuth2User processOAuth2User(OAuth2User oauth2User) {
        OAuth2UserInfo userInfo = new KakaoOAuth2UserInfo(oauth2User.getAttributes());
        Member member = findOrCreateMember(userInfo);

        return new CustomOAuth2Member(member, oauth2User.getAttributes());
    }

    private Member findOrCreateMember(OAuth2UserInfo userInfo) {
        Optional<Member> optionalMember = memberRepository.findBySocialId(userInfo.getSocialId());
        if (optionalMember.isPresent()) return optionalMember.get();

        Member member = Member.createMember(
                userInfo.getName(),
                userInfo.getEmail(),
                userInfo.getSocialId(),
                userInfo.getProfileUrl()
        );
        memberRepository.save(member);

        return member;
    }

}