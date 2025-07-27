package org.chungnamthon.flowmate.domain.challenge.entity;

import static org.springframework.util.Assert.state;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.flowmate.domain.BaseEntity;
import org.chungnamthon.flowmate.domain.challenge.entity.dto.ChallengeCreateDomainRequest;
import org.chungnamthon.flowmate.domain.member.entity.Category;

@Table(name = "challenge")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Challenge extends BaseEntity {

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 100)
    private String introduction;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Category category;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ChallengeStatus status;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private Long maxParticipants;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String rule;

    public static Challenge create(ChallengeCreateDomainRequest request) {
        Challenge challenge = new Challenge();

        challenge.title = request.title();
        challenge.introduction = request.introduction();
        challenge.category = request.category();
        challenge.status = ChallengeStatus.RECRUITING;
        challenge.startDate = request.startDate();
        challenge.endDate = request.endDate();
        challenge.maxParticipants = request.maxParticipants();
        challenge.rule = request.rule();

        return challenge;
    }

    public void updateStatusToInProgress() {
        // IllegalStateException을 던집니다.
        state(this.status == ChallengeStatus.RECRUITING, "모집 중일 때만 변경 가능합니다.");

        this.status = ChallengeStatus.IN_PROGRESS;
    }

    public void updateStatusToComplete() {
        // IllegalStateException을 던집니다.
        state(this.status == ChallengeStatus.IN_PROGRESS, "진행 중일 때만 변경 가능합니다.");

        this.status = ChallengeStatus.COMPLETED;
    }

    public void modifyChallenge(String title, String introduction, Category category, String rule) {
        state(this.status == ChallengeStatus.RECRUITING, "챌린지 변경 기간이 아닙니다.");

        this.title = title;
        this.introduction = introduction;
        this.category = category;
        this.rule = rule;
    }

}