package org.chungnamthon.flowmate.domain.certification.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CertificationRepository {

    private final CertificationJpaRepository certificationJpaRepository;

}