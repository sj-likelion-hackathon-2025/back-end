package org.chungnamthon.flowmate.domain.challengeapplication.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challengeapplication.controller.dto.ChallengeApplicationCreateRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.service.ChallengeApplicationCommandService;
import org.chungnamthon.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/challenges")
@RequiredArgsConstructor
@RestController
public class ChallengeApplicationController {

    private final ChallengeApplicationCommandService challengeApplicationCommandService;

    @PostMapping("/{challengeId}/applications")
    public ResponseEntity<Void> apply(
            @Valid @RequestBody ChallengeApplicationCreateRequest request,
            @AuthMember Long memberId,
            @PathVariable Long challengeId
    ) {
        challengeApplicationCommandService.apply(request.toServiceRequest(memberId, challengeId));

        return ResponseEntity.ok().build();
    }

}