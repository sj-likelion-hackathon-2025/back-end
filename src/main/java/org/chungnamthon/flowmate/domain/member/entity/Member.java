package org.chungnamthon.flowmate.domain.member.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.chungnamthon.flowmate.domain.BaseEntity;

@Table(name = "member")
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Member extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @Enumerated(value = EnumType.STRING)
    private Role role;

    @Enumerated(value = EnumType.STRING)
    private Grade grade;

    @Column(nullable = false)
    private Long point;

    @Column(name = "profile_img_url")
    private String profileImgUrl;

    @Column(name = "social_id", nullable = false)
    private String socialId;

    @Column(name = "refresh_token")
    private String refreshToken;

    public static Member createMember(String email, String name, String profileImgUrl, String socialId) {
        Member member = new Member();

        member.email = email;
        member.name = name;
        member.role = Role.ROLE_GUEST;
        member.grade = Grade.ROOKIE;
        member.point = 0L;
        member.profileImgUrl = profileImgUrl;
        member.socialId = socialId;
        member.refreshToken = null;

        return member;
    }

    public void updatePoint(Long point) {
        long updatedPoint = this.point + point;

        if (updatedPoint < 0) this.point = 0L;
        else this.point = updatedPoint;

        Grade gradeByPoint = Grade.getGradeByPoint(this.point);
        updateGrade(gradeByPoint);
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateProfileImgUrl(String profileImgUrl) {
        this.profileImgUrl = profileImgUrl;
    }

    public void upgradeRoleToMember() {
        this.role = Role.ROLE_MEMBER;
    }

    private void updateGrade(Grade grade) {
        this.grade = grade;
    }

}