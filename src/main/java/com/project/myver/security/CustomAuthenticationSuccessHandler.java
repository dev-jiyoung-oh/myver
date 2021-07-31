package com.project.myver.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.util.StringUtils;

import com.project.myver.service.MemberService;

/* 21.04.09 생성
 * 로그인 성공 후 어떻게 처리할지 관리하는 Handler
 * 참고 사이트 : https://zgundam.tistory.com/52?category=430446
 */
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private RequestCache requestCache = new HttpSessionRequestCache();
	private String targetUrlParameter;
	private String defaultUrl;
	private boolean useReferer;
	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
	
	@Autowired
	private MemberService memSVC;
	
	public CustomAuthenticationSuccessHandler() {
		targetUrlParameter = "";
		defaultUrl = "/";
		useReferer = true;
	}
	
	public String getTargetUrlParameter() {
		return targetUrlParameter;
	}
	
	public void setTargetUrlParameter(String targetUrlParameter) {
		this.targetUrlParameter = targetUrlParameter;
	}
	
	public String getDefaultUrl(String defaultUrl) {
		return defaultUrl;
	}
	
	public void setDefaultUrl(String defaultUrl) {
		this.defaultUrl = defaultUrl;
	}
	
	public boolean isUseReferer() {
		return useReferer;
	}
	
	public void setUseReferer(boolean useReferer) {
		this.useReferer = useReferer;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		clearAuthenticationAttributes(request);
		
		HttpSession session = request.getSession(); // 세션 가져오기
		
		// 21.04.17 최근 접속일 갱신
		memSVC.updateLog_date(request.getParameter("id"));
		
		// 21.05.07 세션에 로그인 한 회원 id 저장
		session.setAttribute("MID", request.getParameter("id"));
		
		int intRedirectStrategy = decideRedirectStrategy(request, response);
		switch(intRedirectStrategy) {
		case 1:
			useTargetUrl(request, response);
			break;
		case 2:
			useSessionUrl(request, response);
			break;
		case 3:
			useSessionUrl2(request, response);
			break;
		case 4:
			useRefererUrl(request, response);
			break;
		default:
			useDefaultUrl(request, response);
		}
	}
	
	private void clearAuthenticationAttributes(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		
		if(session == null) {
			return;
		}
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
	
	private void useTargetUrl(HttpServletRequest request, HttpServletResponse response) 
			throws IOException {
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess - targetUrl = "+targetUrlParameter);
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(savedRequest != null) {
			requestCache.removeRequest(request, response);
		}
		
		String targetUrl = request.getParameter(targetUrlParameter);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useSessionUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		String targetUrl = savedRequest.getRedirectUrl();
		redirectStrategy.sendRedirect(request, response, targetUrl);
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess - sessionUrl = "+ targetUrl);
	}
	
	private void useSessionUrl2(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		String prevPage = (String)session.getAttribute("prevPage");
		session.removeAttribute("prevPage");
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess - prevPage = "+ prevPage);
		redirectStrategy.sendRedirect(request, response, prevPage);
	}
	
	private void useRefererUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetUrl = request.getHeader("REFERER");
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess - refererUrl = "+ targetUrl);
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}
	
	private void useDefaultUrl(HttpServletRequest request, HttpServletResponse response) throws IOException {
		System.out.println("CustomAuthenticationSuccessHandler.onAuthenticationSuccess - defaultUrl");
		redirectStrategy.sendRedirect(request, response, defaultUrl);
	}
	
	/* 인증 성공 후 어떤 URL로 redirect 할지를 결정.
	 * 판단 기준은 
	 * 1순위 : targetUrlParameter값을 읽은 URL이 존재할 경우
	 * 2순위 : 1순위 URL이 없을 경우, Spring Security가 세션에 저장한 URL
	 * 3순위 : 2순위 URL이 없을 경우, 로그인 페이지에서 referer을 저장한 URL이 존재할 경우
	 * 4순위 : 3순위 URL이 없을 경우, Request의 REFERER을 사용하고 그 REFERER URL이 존재할 경우
	 * 5순위 : 4순위 URL이 없을 경우, Default URL
	 * 
	 * @param request
	 * @param response
	 * @return 1 : targetUrlParameter값을 읽은 URL
	 *         2 : Session에 저장되어 있는 URL(1)
	 *         3 : Session에 저장되어 있는 URL(2) - login page에서 선언한 URL
	 *         4 : referer 헤더에 있는 URL
	 *         0 : default URL 
	 */
	private int decideRedirectStrategy(HttpServletRequest request, HttpServletResponse response) {
	
		int result = 0;
		
		SavedRequest savedRequest = requestCache.getRequest(request, response);
		
		if(!"".equals(targetUrlParameter)) {
			String targetUrl = request.getParameter(targetUrlParameter);
			
			if(StringUtils.hasText(targetUrl) && !targetUrl.equals("null")) {
				result = 1;
			}else {
				if(savedRequest != null) {
					result = 2;
				}else {
					HttpSession session = request.getSession();
					
					if(session != null) {
						String prevPage = (String)session.getAttribute("prevPage");
						
						if(prevPage != null) {
							result = 3;
							return result;
						}
					}
					
					String refererUrl = request.getHeader("REFERER");
					
					if(useReferer && StringUtils.hasText(refererUrl)) {
						result = 4;
					}else {
						result = 0;
					}
				}
			}
			
			return result;
		}
		
		if(savedRequest !=null) {
			result = 2;
			return result;
		}
		
		String refererUrl = request.getHeader("REFERER");
		
		if(useReferer && StringUtils.hasText(refererUrl)) {
			result = 3;
		}else {
			result = 0;
		}
		
		return result;
	}

}
