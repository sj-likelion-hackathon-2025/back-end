package org.chungnamthon.flowmate.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.challenge.repository.ChallengeRepository;
import org.chungnamthon.flowmate.domain.challenge.service.dto.ChallengeCreateServiceRequest;
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;
import org.chungnamthon.flowmate.domain.challengeparticipant.repository.ChallengeParticipantRepository;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ChallengeCommandService {

    private final MemberRepository memberRepository;
    private final ChallengeRepository challengeRepository;
    private final ChallengeParticipantRepository challengeParticipantRepository;

    @Transactional
    public Long create(ChallengeCreateServiceRequest request) {
        Member member = memberRepository.findById(request.memberId());
        Challenge challenge = Challenge.create(request.toDomainRequest());
        challengeRepository.save(challenge);

        addChallengeParticipant(member, challenge);

        return challenge.getId();
    }

    private void addChallengeParticipant(Member member, Challenge challenge) {
        ChallengeParticipant participant = ChallengeParticipant.createLeader(member, challenge);
        challengeParticipantRepository.save(participant);
    }

}