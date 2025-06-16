package com.mysite.sbb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

// *AntPathRequestMatcher는 spring security 6.1부터 deprecated되었다
// 당장 오류가 발생하는 것이 아니며, 경고 알람이 뜰 순 있음.
// 이 교재는 6 버전을 사용하기로 했으니 일단 사용하도록 한다.

@Configuration // 이 파일이 스프링의 환경 설정 파일임을 의미함
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받게 함 = 스프링 시큐리티를 활성화
public class SecurityConfig {
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        // 이 SecurityFilterChain 클래스가 동작하여 모든 요청 URL에 이 클래스가 필터로 적용된다.
        // => URL별로 특별한 설정을 할 수 있게 한다.
        // 세부 설정은 @Bean 애너테이션을 통해 SecurityFilterChain 빈을 생성하여 설정할 수 있다.
        // Bean이란? 스프링에 의해 생성 또는 관리되는 객체 (컨트롤러, 서비스, 리포지터리 등등..)
        // @Bean 애너테이션을 통해 별도로 빈을 정의, 등록할 수 있는 것이다.
        http
                .authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
                        .requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
                .csrf((csrf) -> csrf
                    .ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**")))
                .headers((headers) -> headers
                        .addHeaderWriter(new XFrameOptionsHeaderWriter(
                                XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
        ;
        // 인증되지 않은 모든 페이지("/**")의 요청을 허락한다는 뜻.
        // 이렇게 써놔야 로그인하지 않아도 모든 페이지에 접근할 수 있게 됨.
        // (기존에 springsecurity를 적용시키고 그냥 실행하면 로그인화면같은게 나온다.
        return http.build();
    }
}
