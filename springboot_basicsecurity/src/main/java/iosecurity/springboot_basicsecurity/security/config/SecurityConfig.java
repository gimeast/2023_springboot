package iosecurity.springboot_basicsecurity.security.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

//    private final UserDetailsService userDetailsService;

//    @Autowired
//    public SecurityConfig(UserDetailsService userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService inMemoryUsers() {
        UserDetails user = User.builder()
                .username("user")
                .password(passwordEncoder().encode("1"))
                .roles("USER")
                .build();
        UserDetails sys = User.builder()
                .username("sys")
                .password(passwordEncoder().encode("1"))
                .roles("SYS", "USER")
                .build();
        UserDetails admin = User.builder()
                .username("admin")
                .password(passwordEncoder().encode("1"))
                .roles("ADMIN", "SYS", "USER")
                .build();

        return new InMemoryUserDetailsManager(user, sys, admin);
    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                    .authorizeRequests() // 인가정책
                    .antMatchers("/user").hasRole("USER")
                    .antMatchers("/admin/pay").hasRole("ADMIN")
                    .antMatchers("/admin/**").access("hasRole('ADMIN') or hasRole('SYS')")
                    .anyRequest().authenticated() //어떤 요청이든 인증이 되었는지를 확인하는 설정
                .and()
                    .formLogin() // 인증정책
//                    .loginPage("/loginPage")
                    .usernameParameter("userId")
                    .passwordParameter("passwd")
                    .defaultSuccessUrl("/")
                    .failureUrl("/loginPage")
                    .loginProcessingUrl("/login_proc") // form의 action url
                    .successHandler(new AuthenticationSuccessHandler() {
                        @Override
                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            log.info("[onAuthenticationSuccess] authentication.getName(): {}", authentication.getName()); // 인증에 성공한 username
                            response.sendRedirect("/"); // 인증에 성공후 root 페이지로 이동
                        }
                    })
                    .failureHandler(new AuthenticationFailureHandler() {
                        @Override
                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                            log.info("[onAuthenticationFailure] exception.getMessage(): {}", exception.getMessage());
                            response.sendRedirect("/loginPage");
                        }
                    })
                    .permitAll()
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/loginPage")
                    .addLogoutHandler(new LogoutHandler() {
                        @Override
                        public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                            HttpSession session = request.getSession();
                            session.invalidate();
                        }
                    })
                    .logoutSuccessHandler(new LogoutSuccessHandler() {
                        @Override
                        public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                            response.sendRedirect("/loginPage");
                        }
                    })
                    .deleteCookies("remember-me")
                .and()
                    .rememberMe()
                    .rememberMeParameter("remember") //파라미터
                    .tokenValiditySeconds(3600) //1시간
                    .userDetailsService(inMemoryUsers())
//                    .userDetailsService(userDetailsService)
                    .alwaysRemember(false)
                .and()
                //[동시 세션 제어 전략] 전략1.이전 사용자 세션만료 / 전략2.현재 사용자 인증 실패
                    .sessionManagement() //세션관리 기능이 작동한다
                    .maximumSessions(1) //최대 허용 가능 세션 수 -1:무제한 로그인세션 허용
                    .maxSessionsPreventsLogin(true) //true:동시 로그인 차단함 2번전략, false:기존 세션 만료(default) 1번전략
//                    .expiredUrl("/expired") //세션이 만료된 경우 이동 할 페이지
                    .and()
//                    .invalidSessionUrl("/invalid") //세션이 유효하지 않을때 이동할 페이지

//                    .sessionFixation() //[세션고정보호]
//                      .none() //이 none 설정은 브라우저 공격을 받을 수 있다. 위험하다.
//                    .changeSessionId() //기본값. 세션 고정 보호. 로그인 하면 새로운 세션을 생성한다. 안전하다.
                .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer configure() { //인증과 인가가 모두 적용되기전에 동작하는 설정이다
        return web -> web.ignoring()
                .antMatchers("/expired", "/invalid", "/loginPage");//인증, 인가를 무시하는 경로를 설정한다
    }

}
