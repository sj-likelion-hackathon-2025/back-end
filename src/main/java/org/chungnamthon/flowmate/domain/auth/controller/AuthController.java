package org.chungnamthon.flowmate.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.auth.controller.dto.ReissueTokenRequest;
import org.chungnamthon.flowmate.domain.auth.service.AuthService;
import org.chungnamthon.flowmate.global.security.jwt.dto.TokenResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/reissue")
    public ResponseEntity<TokenResponse> reissue(@Valid @RequestBody ReissueTokenRequest request) {
        TokenResponse response = authService.reissue(request.toServiceRequest());

        return ResponseEntity.ok(response);
    }

}