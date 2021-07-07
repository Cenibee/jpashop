package com.cenibee.inflearn.jpashop.service;

import com.cenibee.inflearn.jpashop.domain.Member;
import com.cenibee.inflearn.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("회원가입")
    public void 회원가입() {
        //given
        Member member = new Member();
        member.setUsername("kim");

        //when
        Long savedId = memberService.join(member);

        //then
        assertThat(member).isEqualTo(memberRepository.findOne(savedId));
    }

    @Test
    @DisplayName("중복 회원 예외")
    public void 중복_회원_예외() {
        //given
        Member member1 = new Member();
        member1.setUsername("kim1");

        Member member2 = new Member();
        member2.setUsername("kim1");

        //when
        memberService.join(member1);

        //then
        assertThrows(IllegalStateException.class, () -> memberService.join(member2));
    }

}