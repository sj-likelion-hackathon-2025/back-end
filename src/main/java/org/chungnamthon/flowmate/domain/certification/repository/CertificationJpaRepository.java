package org.chungnamthon.flowmate.domain.certification.repository;

import org.chungnamthon.flowmate.domain.certification.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificationJpaRepository extends JpaRepository<Certification, Long> {

}