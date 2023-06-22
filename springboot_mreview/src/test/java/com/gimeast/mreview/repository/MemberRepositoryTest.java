package com.gimeast.mreview.repository;

import com.gimeast.mreview.entity.Member;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.IntStream;

@SpringBootTest
@Transactional
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Test
    @Rollback(value = false)
    void 회원등록() throws Exception {

        IntStream.rangeClosed(1, 100).forEach(i -> {
            Member member = Member.builder()
                    .email("r"+i+"@gmail.com")
                    .pw("1111")
                    .nickname("reviewer"+i)
                    .build();

            memberRepository.save(member);
        });

    }

    @Test
    @Rollback(value = false)
    void 회원삭제() throws Exception {
        //given
        Long mid = 2L;

        //when
        reviewRepository.deleteByMemberMid(mid);
        memberRepository.deleteById(mid);
        //then

    }


}