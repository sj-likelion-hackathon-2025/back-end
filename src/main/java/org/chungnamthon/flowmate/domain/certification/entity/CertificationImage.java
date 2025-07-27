package org.chungnamthon.flowmate.domain.certification.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.chungnamthon.flowmate.domain.BaseEntity;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class CertificationImage extends BaseEntity {

    @Column(nullable = false)
    Long certificationId;

    @Column(nullable = false)
    String image;

    public static CertificationImage create(Long certificationId, String image) {
        return new CertificationImage(certificationId, image);
    }

}