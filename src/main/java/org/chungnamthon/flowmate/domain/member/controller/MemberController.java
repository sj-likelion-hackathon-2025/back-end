package org.chungnamthon.flowmate.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.controller.dto.MemberCheckNameRequest;
import org.chungnamthon.flowmate.domain.member.controller.dto.MemberProfileRegisterRequest;
import org.chungnamthon.flowmate.domain.member.service.MemberCommandService;
import org.chungnamthon.flowmate.domain.member.service.MemberQueryService;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberProfileResponse;
import org.chungnamthon.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("/members")
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @PostMapping
    public ResponseEntity<Void> register(
            @Valid @RequestPart MemberProfileRegisterRequest request,
            @RequestPart MultipartFile image,
            @AuthMember Long memberId
    ) {
        memberCommandService.register(request.toServiceRequest(image, memberId));

        return ResponseEntity.ok().build();
    }

    @PostMapping("/name")
    public ResponseEntity<Void> checkNameDuplicate(MemberCheckNameRequest request) {
        memberQueryService.checkNameDuplicate(request.name());

        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<MemberProfileResponse> getProfile(@AuthMember Long memberId) {
        MemberProfileResponse response = memberQueryService.getProfile(memberId);

        return  ResponseEntity.ok(response);
    }

}