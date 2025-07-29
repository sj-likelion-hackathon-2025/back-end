package org.chungnamthon.flowmate.domain.member.service.dto;

import lombok.Builder;
import org.chungnamthon.flowmate.domain.member.entity.Grade;

@Builder
public record MemberProfileResponse(String name, String image, Grade grade, Long point) {

}