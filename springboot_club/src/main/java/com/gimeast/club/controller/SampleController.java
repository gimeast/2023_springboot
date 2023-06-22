package com.gimeast.club.controller;

import com.gimeast.club.security.dto.ClubAuthMemberDto;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@Log4j2
@RequestMapping("/sample")
public class SampleController {

    @GetMapping("/all")
    @PreAuthorize("permitAll()")
    public void exAll() {
        log.info("exAll.......");
    }

    @GetMapping("/member")//@AuthenticationPrincipal를 사용하면 로그인된 사용자정보를 확인할수있다.
    @PreAuthorize("hasRole('USER')")
    public void exMember(@AuthenticationPrincipal ClubAuthMemberDto clubAuthMemberDto) {
        log.info("exMember.......");
        log.info("------------------------");
        log.info(clubAuthMemberDto);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public void exAdmin() {
        log.info("exAdmin.......");
    }

    @GetMapping("/exOnly")
    @PreAuthorize("#clubAuthMemberDto != null && #clubAuthMemberDto.username eq \"user95@gmail.com\"")
    public String exMemberOnly(@AuthenticationPrincipal ClubAuthMemberDto clubAuthMemberDto) {
        log.info("exMemberOnly.......");
        log.info(clubAuthMemberDto);
        return "/sample/admin";
    }
}
