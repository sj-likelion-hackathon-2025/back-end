package org.chungnamthon.flowmate.global.security.jwt.dto;

import lombok.Builder;

@Builder
public record TokenResponse(String accessToken, String refreshToken) {

}