package com.project.myver.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/* 21.04.10
 * 로그인 인증 실패 핸들러
 * 참고 : https://zgundam.tistory.com/53?category=430446
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

	private String loginIdName;			// 로그인 id값이 들어오는 input 태그 name
	private String loginPwName;			// 로그인 password값이 들어오는 input 태그 name
	private String loginRedirectName;	// 로그인 성공시 redirect할 URL이 지정되어 있는 input 태그 name
	private String exceptionMsgName;	// 예외 메시지를 request의 Attribute에 저장할 때 사용될 key값
	private String defaultFailureUrl;	// 화면에 보여줄 URL(로그인 화면)
	
	
	public CustomAuthenticationFailureHandler() {
		this.loginIdName = "id";
		this.loginPwName = "pw";
		this.loginRedirectName = "loginRedirect";
		this.exceptionMsgName = "securityExceptionMsg";
		this.defaultFailureUrl = "/login";
	}
	
	public String getLoginIdName() {
		return loginIdName;
	}

	public void setLoginIdName(String loginIdName) {
		this.loginIdName = loginIdName;
	}

	public String getLoginPwName() {
		return loginPwName;
	}

	public void setLoginPwName(String loginPwName) {
		this.loginPwName = loginPwName;
	}

	public String getLoginRedirectName() {
		return loginRedirectName;
	}

	public void setLoginRedirectName(String loginRedirectName) {
		this.loginRedirectName = loginRedirectName;
	}

	public String getExceptionMsgName() {
		return exceptionMsgName;
	}

	public void setExceptionMsgName(String exceptionMsgName) {
		this.exceptionMsgName = exceptionMsgName;
	}

	public String getDefaultFailureUrl() {
		return defaultFailureUrl;
	}

	public void setDefaultFailureUrl(String defaultFailureUrl) {
		this.defaultFailureUrl = defaultFailureUrl;
	}

	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		// Request객체의 Attribute에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를 저장해두어 로그인 id와 pw를 저장해두어 로그인 페이지에서 이를 접근하도록 함.
		String loginId = request.getParameter(loginIdName);
		String loginPw = request.getParameter(loginPwName);
		String loginRedirect = request.getParameter(loginRedirectName);
		
		request.setAttribute(loginIdName, loginId);
		request.setAttribute(loginPwName, loginPw);
		request.setAttribute(loginRedirectName, loginRedirect);
		request.setAttribute(exceptionMsgName, exception.getMessage()); // Request객체의 Attribute에 예외 메시지 저장

		/* HttpServletResponse클래스의 sendRedirect메소드를 이용해서 redirect하지 않고,
		 * HttpServletRequest클래스의 getRequestDispatcher메소드를 이용해서 보여줘야 할 화면으로 forward를 해주는 이유 >>
		 *  - forward는 요청정보가 그대로 살아있어 똑같은 글이 여러번 등록될 수 있지만, 
		 *    redirect는 처음 글을 작성할 때 보냈던 요청정보가 존재하지 않음.
		 *    forward로 해줘야 jstl을 이용해서 setAttribute로 저장한 값을 가져올 수 있음!!
		 */
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
		
	}

}
