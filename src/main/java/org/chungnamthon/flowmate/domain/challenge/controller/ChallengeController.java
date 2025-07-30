package org.chungnamthon.flowmate.domain.challenge.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.controller.dto.ChallengeCreateRequest;
import org.chungnamthon.flowmate.domain.challenge.service.ChallengeCommandService;
import org.chungnamthon.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/challenges")
@RequiredArgsConstructor
@RestController
public class ChallengeController {

    private final ChallengeCommandService challengeCommandService;

    @PostMapping
    public ResponseEntity<Void> create(
            @Valid @RequestBody ChallengeCreateRequest request,
            @AuthMember Long memberId
    ) {
        challengeCommandService.create(request.toServiceRequest(memberId));

        return ResponseEntity.ok().build();
    }

}