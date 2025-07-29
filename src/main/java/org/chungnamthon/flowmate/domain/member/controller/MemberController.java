package org.chungnamthon.flowmate.domain.member.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.controller.dto.MemberCreateRequest;
import org.chungnamthon.flowmate.domain.member.service.MemberCommandService;
import org.chungnamthon.flowmate.global.annotation.AuthMember;
import org.springframework.http.ResponseEntity;
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

    @PostMapping
    public ResponseEntity<Void> register(
            @Valid @RequestPart MemberCreateRequest request,
            @RequestPart MultipartFile image,
            @AuthMember Long memberId
    ) {
        memberCommandService.register(request.toServiceRequest(image, memberId));

        return ResponseEntity.ok().build();
    }

}