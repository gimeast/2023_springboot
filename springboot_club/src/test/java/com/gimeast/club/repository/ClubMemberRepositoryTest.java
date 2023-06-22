package com.gimeast.club.repository;

import com.gimeast.club.entity.ClubMember;
import com.gimeast.club.entity.ClubMemberRole;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ClubMemberRepositoryTest {
    
    @Autowired
    private ClubMemberRepository clubMemberRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Test
    @Rollback(value = false)
    void 더미_데이터_생성() throws Exception {

        IntStream.range(1, 100).forEach(i -> {
            ClubMember clubMember = ClubMember.builder()
                    .email("user" + i + "@gmail.com")
                    .name("사용자" + i)
                    .fromSocial(false)
                    .password(passwordEncoder.encode("1111"))
                    .build();

            clubMember.addMemberRole(ClubMemberRole.USER);

            if (i > 80) {
                clubMember.addMemberRole(ClubMemberRole.MANAGER);
            }

            if (i > 90) {
                clubMember.addMemberRole(ClubMemberRole.ADMIN);
            }

            clubMemberRepository.save(clubMember);
        });

    }

    @Test
    void testRead() throws Exception {
        //given
        Optional<ClubMember> result = clubMemberRepository.findByEmail("user95@gmail.com", false);

        //when
        ClubMember clubMember = result.get();

        //then
        System.out.println("=========================");
        System.out.println(clubMember);
        System.out.println("=========================");

    }



}