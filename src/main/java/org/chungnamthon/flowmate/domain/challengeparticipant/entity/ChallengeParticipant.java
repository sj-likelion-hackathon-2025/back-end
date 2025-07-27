package org.chungnamthon.flowmate.domain.challengeparticipant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.flowmate.domain.BaseEntity;
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.member.entity.Member;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access =  AccessLevel.PRIVATE)
@Entity
public class ChallengeParticipant extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeRole role;

    public static ChallengeParticipant createLeader(Member member, Challenge challenge) {
        ChallengeParticipant challengeParticipant = new ChallengeParticipant();

        challengeParticipant.member = member;
        challengeParticipant.challenge = challenge;
        challengeParticipant.role = ChallengeRole.LEADER;

        return challengeParticipant;
    }

    public static ChallengeParticipant createGeneral(Member member, Challenge challenge) {
        ChallengeParticipant challengeParticipant = new ChallengeParticipant();

        challengeParticipant.member = member;
        challengeParticipant.challenge = challenge;
        challengeParticipant.role = ChallengeRole.GENERAL;

        return challengeParticipant;
    }

    public void updateRole(ChallengeRole role) {
        this.role = role;
    }

}
