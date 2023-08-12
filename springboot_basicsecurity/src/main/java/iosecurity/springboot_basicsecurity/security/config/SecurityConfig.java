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
import org.springframework.security.core.userdetails.UserDetailsService;
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

    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                    .authorizeRequests()
                    .anyRequest().authenticated() // 인가정책
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
                    .rememberMeParameter("remember")
                    .tokenValiditySeconds(3600)
                    .userDetailsService(userDetailsService)
                    .alwaysRemember(false)
                    .and()
                .build();
    }

    @Bean
    public WebSecurityCustomizer configure() { //인증과 인가가 모두 적용되기전에 동작하는 설정이다
        return web -> web.ignoring()
                .antMatchers("/api-docs/**","/swagger-ui/**" ,"/sign-api/exception");//인증, 인가를 무시하는 경로를 설정한다
    }

}
