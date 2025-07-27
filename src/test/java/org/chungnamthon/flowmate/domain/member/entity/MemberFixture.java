package org.chungnamthon.flowmate.domain.member.entity;

import org.springframework.test.util.ReflectionTestUtils;

public class MemberFixture {

    public static Member createMember(String name) {
        return Member.createMember("test@email.com", name, "testImageUrl", "123456");
    }

    public static Member createMember() {
        return createMember("kwak");
    }

    public static Member createMember(Long id) {
        Member member = createMember();
        ReflectionTestUtils.setField(member, "id", id);

        return member;
    }

}