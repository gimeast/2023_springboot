package iosecurity.springboot_basicsecurity.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeRequests()
                .anyRequest().authenticated() // 인가정책
                .and()
                .formLogin() // 인증정책
                .and().build();
    }

    @Bean
    public WebSecurityCustomizer configure() { //인증과 인가가 모두 적용되기전에 동작하는 설정이다
        return web -> web.ignoring()
                .antMatchers("/api-docs/**","/swagger-ui/**" ,"/sign-api/exception");//인증, 인가를 무시하는 경로를 설정한다
    }

}
