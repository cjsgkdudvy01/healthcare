package hong.yeongpyo.healthCare.springSecurity.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * 인증 실패 핸들러
 * @author TerryChang
 *
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String loginidname;				// 로그인 id값이 들어오는 input 태그 name
	private String loginpasswdname;			// 로그인 password 값이 들어오는 input 태그 name
	private String loginredirectname;		// 로그인 성공시 redirect 할 URL이 지정되어 있는 input 태그 name
	private String exceptionmsgname;		// 예외 메시지를 request의 Attribute에 저장할 때 사용될 key 값
	private String defaultFailureUrl;		// 화면에 보여줄 URL(로그인 화면)
	
	public CustomAuthenticationFailureHandler(String loginidname, String loginpasswdname, String loginredirectname, String exceptionmsgname, String defaultFailureUrl) {
		this.loginidname = loginidname; 			
		this.loginpasswdname = loginpasswdname;		
		this.loginredirectname = loginredirectname;
		this.exceptionmsgname = exceptionmsgname;	
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {	
		// Request 객체의 Attribute에 사용자가 실패시 입력했던 로그인 ID와 비밀번호를 저장해두어 로그인 페이지에서 이를 접근하도록 한다
		
		String loginid = request.getParameter(loginidname);
		String loginpasswd = request.getParameter(loginpasswdname);
		String loginRedirect = request.getParameter(loginredirectname);
		
		request.setAttribute(loginidname, loginid);
		request.setAttribute(loginpasswdname, loginpasswd);
		request.setAttribute(loginredirectname, loginRedirect);
		
		
		// Request 객체의 Attribute에 예외 메시지 저장
		request.setAttribute(exceptionmsgname, exception.getMessage());
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}