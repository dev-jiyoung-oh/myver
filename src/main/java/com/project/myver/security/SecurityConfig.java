package com.project.myver.security;
/* 2021.01.14
 * WebSecurityConfigurerAdapter를 상속받은 SecurityConfig 만들기
 * 
 * [ 스프링 시큐리티 필터 연결 ]
    WebSecurity 객체는
    springSecurityFilterChain이라는 이름의 DelegatingFilterProxy Bean 객체를 생성한다.
    DelegatingFilterProxy Bean은 많은 Spring Security Filter Chain에 역할을 위임한다.
   
 * 참고사이트
 * https://jungeunlee95.github.io/java/2019/07/17/2-Spring-Security/ 
 * https://victorydntmd.tistory.com/328
 * 
 * 
 * 
 * 2021.01.17
 * "org.springframework.beans.factory.NoSuchBeanDefinitionException: No bean named 'springSecurityFilterChain' is defined"
 * 오류 해결을 못하겠다. Java Configuration 사용하는 방식 포기. XML 파일 사용하는 방식으로 하겠음.
 * 오류 원인 못찾음.
 * 
 * */

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.*;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	// WebSecurityConfigurerAdapter : Spring Security의 설정파일로서의 역할을 하기 위해 상속해야 하는 클래스.

	//private MemberService memSVC;
//	@Autowired
//	private SecurityUserService securityUserSVC;
	
//	@Bean
//    public PasswordEncoder passwordEncoder() {
//		// BCryptPasswordEncoder : Spring Security에서 제공하는 비밀번호 암호화 객체.
//		// Service에서 비밀번호를 암호화할 수 있도록 Bean으로 등록
//        return new BCryptPasswordEncoder();
//    }
	
    @Override
    public void configure(WebSecurity web) throws Exception {
    	// WebSecurity : FilterChainProxy를 생성하는 필터
        // 스프링 시큐리티의 필터 연결을 설정하기 위한 오버라이딩이다.
        // 예외가 웹접근 URL를 설정한다.
        // ACL(Access Control List - 접근 제어 목록)의 예외 URL을 설정
    	
    	super.configure(web); // 아무런 작업을 하지 않음
    	
    	// static 디렉터리의 하위 파일 목록은 인증 무시 ( = 항상통과 )
        //web.ignoring().antMatchers("/css/**", "/js/**", "/img/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 인터셉터로 요청을 안전하게 보호하는 방법을 설정하기 위한 오버라이딩이다.
        super.configure(http); // 모든 url 막고있음
    	
    	/*http.authorizeRequests()
        	// 페이지 권한 설정
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/user/**").hasRole("MEMBER")
            .antMatchers("/**").permitAll()
        .and() // 로그인 설정
            .formLogin()
            .loginPage("/login") // 로그인 URL을 정의
            .failureUrl("/user/login?result=fail") // 로그인 실패시 redirect
            .defaultSuccessUrl("/")
            .usernameParameter("id") // form id의 name 속성값
            .passwordParameter("pw") // form pw의 name 속성값
            //.permitAll() // 모든 사용자가 접근을 허용하도록 설정
        .and() // 로그아웃 설정
            .logout()
        	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
        	.logoutSuccessUrl("/")
        	.invalidateHttpSession(true)
    	.and() // 403 예외처리 핸들링
            .exceptionHandling().accessDeniedPage("/denied");*/
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 사용자 세부 서비스를 설정하기 위한 오버라이딩이다.
        super.configure(auth);
    	
    	//auth.userDetailsService(securityUserSVC).passwordEncoder(passwordEncoder());
    }
}