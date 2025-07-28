package org.chungnamthon.flowmate.global.security.oauth2.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.entity.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

@AllArgsConstructor
public class CustomOAuth2Member implements OAuth2User {

    private final Member member;
    private final Map<String, Object> attributes;

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add((GrantedAuthority) () -> member.getRole().name());

        return collection;
    }

    @Override
    public String getName() {
        return member.getName();
    }

    public Role getRole() {
        return member.getRole();
    }

    public Long getMemberId() {
        return member.getId();
    }

}