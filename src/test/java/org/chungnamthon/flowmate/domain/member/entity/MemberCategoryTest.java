package org.chungnamthon.flowmate.domain.member.entity;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MemberCategoryTest {

    @DisplayName("회원 카테고리를 생성한다.")
    @Test
    void create() {
        var memberId = 1L;
        var category = Category.EXERCISE;

        var memberCategory = MemberCategory.create(memberId, category);

        assertThat(memberCategory)
                .extracting(MemberCategory::getMemberId, MemberCategory::getCategory)
                .containsExactly(              memberId,                    category);
    }

}