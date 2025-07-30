package org.chungnamthon.flowmate.domain.challengeapplication.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.entity.ChallengeApplication;
import org.chungnamthon.flowmate.domain.challengeapplication.repository.ChallengeApplicationRepository;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationApproveServiceRequest;
import org.chungnamthon.flowmate.domain.challengeapplication.service.dto.ChallengeApplicationCreateServiceRequest;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.domain.challengeparticipant.repository.ChallengeParticipantRepository;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.global.exception.DuplicationException;
import org.chungnamthon.flowmate.global.exception.ForbiddenException;
import org.chungnamthon.flowmate.global.exception.dto.ErrorStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeApplicationCommandService {

    private final ChallengeApplicationRepository challengeApplicationRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;
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

    @Transactional
    public void approve(ChallengeApplicationApproveServiceRequest request) {
        ChallengeParticipant participant = challengeParticipantRepository.findByMemberIdAndChallengeId(request.leaderId(), request.challengeId());
        checkLeader(participant);

        ChallengeApplication application = challengeApplicationRepository.findById(request.applicationId());
        // 이미 승인 여부 결정되었던 신청이라면 IllegalStateException을 던진다.
        try {
            application.updateStatus(request.status());
        } catch (IllegalStateException e) {
            throw new DuplicationException(ErrorStatus.ALREADY_DECISION_APPLICATION, e);
        }
    }

    private void checkDuplicateApplication(Long memberId, Long challengeId) {
        if (challengeApplicationRepository.existsByMemberIdAndChallengeId(memberId, challengeId)) {
            throw new DuplicationException(ErrorStatus.DUPLICATE_APPLICATION);
        }
    }

    private void checkLeader(ChallengeParticipant participant) {
        if (participant.isLeader()) {
            return;
        }
        throw new ForbiddenException(ErrorStatus.FORBIDDEN_ERROR);
    }

}