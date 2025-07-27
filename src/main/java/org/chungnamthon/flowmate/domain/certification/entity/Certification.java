package org.chungnamthon.flowmate.domain.certification.entity;

import static org.springframework.util.Assert.state;

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
import org.chungnamthon.flowmate.domain.challengeparticipant.entity.ChallengeParticipant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Certification extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_participant_id")
    private ChallengeParticipant challengeParticipant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private CertificationStatus status;

    public static Certification create(ChallengeParticipant challengeParticipant) {
        return new Certification(challengeParticipant, CertificationStatus.PENDING);
    }

    public void updateStatus(CertificationStatus status) {
        state(this.status == CertificationStatus.PENDING, "인증 결과를 변경 할 수 없습니다.");

        this.status = status;
    }

}