package org.chungnamthon.flowmate.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberCreateServiceRequest;
import org.chungnamthon.flowmate.infrastructure.s3.S3Provider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class MemberCommandService {

    private final MemberRepository memberRepository;
    private final S3Provider s3Provider;

    @Transactional
    public void updateRefreshToken(Long memberId, String refreshToken) {
        Member member = memberRepository.findById(memberId);
        member.updateRefreshToken(refreshToken);
    }

    @Transactional
    public void register(MemberCreateServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());
        String uploadImage = s3Provider.uploadImage(request.image());

        member.updateProfileImgUrl(uploadImage);
        member.updateName(request.name());
        member.upgradeRoleToMember();
    }

}