package org.chungnamthon.flowmate.domain.challengeapplication.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ChallengeApplication;
import org.chungnamthon.flowmate.domain.challengeapplication.repository.ChallengeApplicationRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChallengeApplicationCommandService {

    private final ChallengeApplicationRepository challengeApplicationRepository;
    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;

    public void apply(ChallengeApplicationCreateServiceRequest request) {
        Long memberId = request.memberId();
        Long challengeId = request.challengeId();

        Member member = memberRepository.findById(memberId);
        Challenge challenge = challengeRepository.findById(challengeId);

        checkDuplicateApplication(memberId, challengeId);

        ChallengeApplication application = ChallengeApplication.create(member, challenge, request.message());
        challengeApplicationRepository.save(application);
    }

    private void checkDuplicateApplication(Long memberId, Long challengeId) {
        if (challengeApplicationRepository.existsByMemberIdAndChallengeId(memberId, challengeId)) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_APPLICATION);
        }
    }

}