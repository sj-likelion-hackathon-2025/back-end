package org.chungnamthon.flowmate.domain.member.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.chungnamthon.flowmate.domain.member.entity.Role.ROLE_GUEST;
import static org.chungnamthon.flowmate.domain.member.entity.Role.ROLE_MEMBER;

import jakarta.persistence.EntityManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.chungnamthon.flowmate.domain.member.entity.Member;
import org.chungnamthon.flowmate.domain.member.entity.MemberFixture;
import org.chungnamthon.flowmate.domain.member.repository.MemberRepository;
import org.chungnamthon.flowmate.domain.member.service.dto.MemberCreateServiceRequest;
import org.chungnamthon.flowmate.global.exception.NotFoundException;
import org.chungnamthon.flowmate.infrastructure.s3.S3Provider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest
record MemberCommandServiceTest(
        MemberCommandService memberCommandService,
        MemberRepository memberRepository,
        S3Provider s3Provider,
        EntityManager entityManager
) {
    // TODO: 과연 진짜 S3로 테스트하는게맞을까?
    @DisplayName("회원 정보를 등록한다.")
    @Test
    void register() throws IOException {
        Member member = MemberFixture.createMember();
        memberRepository.save(member);

        MockMultipartFile file = getMockMultipartFile();
        MemberCreateServiceRequest request = new MemberCreateServiceRequest(file, "updateName", member.getId());

        assertThat(member.getName()).isEqualTo("kwak");
        assertThat(member.getRole()).isEqualTo(ROLE_GUEST);

        memberCommandService.register(request);

        assertThat(member.getName()).isEqualTo("updateName");
        assertThat(member.getRole()).isEqualTo(ROLE_MEMBER);
    }

    @DisplayName("회원이 존재하지 않아 예외를 반환한다.")
    @Test
    void failRegister() throws IOException {
        MockMultipartFile file = getMockMultipartFile();
        MemberCreateServiceRequest request = new MemberCreateServiceRequest(file, "updateName", 9999L);

        assertThatThrownBy(() -> memberCommandService.register(request))
            .isInstanceOf(NotFoundException.class);
    }

    private MockMultipartFile getMockMultipartFile() throws IOException {
        File image = new File("src/test/resources/test.png");
        return new MockMultipartFile(
                "image",                         // 파라미터 이름
                "test.png",                      // 파일 이름
                "image/png",                    // Content-Type
                new FileInputStream(image)
        );
    }

}