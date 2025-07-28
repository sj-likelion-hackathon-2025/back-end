package org.chungnamthon.flowmate.global.security.jwt.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum TokenType {

    AUTHORIZATION_HEADER("Authorization"),
    BEARER_PREFIX("Bearer "),
    ACCESS("accessToken"),
    REFRESH("refreshToken"),
    ;

    private final String value;

}