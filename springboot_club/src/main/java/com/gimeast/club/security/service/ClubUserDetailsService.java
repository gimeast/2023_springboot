package com.gimeast.club.security.service;

import com.gimeast.club.entity.ClubMember;
import com.gimeast.club.repository.ClubMemberRepository;
import com.gimeast.club.security.dto.ClubAuthMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Collectors;

/**
 * ID와 PW를 이용한 로그인
 */
@Log4j2
@Service
public class ClubUserDetailsService implements UserDetailsService {

    private final ClubMemberRepository clubMemberRepository;

    public ClubUserDetailsService(ClubMemberRepository clubMemberRepository) {
        this.clubMemberRepository = clubMemberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("ClubUserDetailsService loadUserByUsername "+ username);

        Optional<ClubMember> result = clubMemberRepository.findByEmail(username, false);
        if (result.isEmpty()) {
            throw new UsernameNotFoundException("Check Email or Social");
        }
        ClubMember clubMember = result.get();

        log.info("---------------------------------");
        log.info(clubMember);

        ClubAuthMemberDto clubAuthMemberDto = new ClubAuthMemberDto(
                clubMember.getEmail(),
                clubMember.getPassword(),
                clubMember.isFromSocial(),
                clubMember.getRoleSet().stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name())).collect(Collectors.toList())
        );

        clubAuthMemberDto.setName(clubMember.getName());
        clubAuthMemberDto.setFromSocial(clubMember.isFromSocial());

        return clubAuthMemberDto;
    }
}
