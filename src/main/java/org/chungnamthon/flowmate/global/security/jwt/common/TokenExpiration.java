package org.chungnamthon.flowmate.global.security.jwt.common;

import lombok.Getter;

@Getter
public enum TokenExpiration {

    ACCESS_TOKEN  (2 * 60 * 60 * 1000L),      // 2시간
    REFRESH_TOKEN (7 * 24 * 60 * 60 * 1000L), // 1주일
  ;

    private final long expirationTime;

    TokenExpiration(long expirationTime) {
        this.expirationTime = expirationTime;
    }

}