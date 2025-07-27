package org.chungnamthon.flowmate.domain.challengeapplication.entity;

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
import org.chungnamthon.flowmate.domain.challenge.entity.Challenge;
import org.chungnamthon.flowmate.domain.member.entity.Member;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access =  AccessLevel.PROTECTED)
@Entity
public class ChallengeApplication extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    public static ChallengeApplication create(Member member, Challenge challenge, String message) {
        ChallengeApplication  challengeApplication = new ChallengeApplication();

        challengeApplication.member = member;
        challengeApplication.challenge = challenge;
        challengeApplication.message = message;
        challengeApplication.status = ApplicationStatus.PENDING;

        return challengeApplication;
    }

    public void updateStatus(ApplicationStatus status) {
        state(this.status == ApplicationStatus.PENDING, "승인 여부를 변경 할 수 없습니다.");

        this.status = status;
    }

}