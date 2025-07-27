package org.chungnamthon.flowmate.domain.certification.service;

import lombok.RequiredArgsConstructor;
import org.chungnamthon.flowmate.domain.certification.repository.CertificationRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CertificationCommandService {

    private final CertificationRepository certificationRepository;

}