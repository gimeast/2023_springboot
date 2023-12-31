package com.gimeast.club.security.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@Log4j2
@Getter
@Setter
@ToString
public class ClubAuthMemberDto extends User implements OAuth2User {

    private String email;
    private String password;
    private String name;
    private boolean fromSocial;//SNS 로그인 여부

    private Map<String, Object> attr;//SNS 로그인의 OAuth2User를 위한

    /**
     * SNS 로그인에 사용
     */
    public ClubAuthMemberDto(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities,
                             Map<String, Object> attr) {
        this(username,password,fromSocial,authorities);
        this.attr = attr;
    }

    /**
     * 일반 로그인에 사용
     */
    public ClubAuthMemberDto(String username,
                             String password,
                             boolean fromSocial,
                             Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.email = username;
        this.password = password;
        this.fromSocial = fromSocial;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return this.attr;
    }
}
