package org.chungnamthon.flowmate.domain.member.service.dto;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
public record MemberCreateServiceRequest(MultipartFile image, String name, Long memberId) {

}