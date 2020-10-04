package hong.yeongpyo.healthCare.springSecurity.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

/**
 * ���� ���� �ڵ鷯
 * @author TerryChang
 *
 */
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
	
	private String loginidname;				// �α��� id���� ������ input �±� name
	private String loginpasswdname;			// �α��� password ���� ������ input �±� name
	private String loginredirectname;		// �α��� ������ redirect �� URL�� �����Ǿ� �ִ� input �±� name
	private String exceptionmsgname;		// ���� �޽����� request�� Attribute�� ������ �� ���� key ��
	private String defaultFailureUrl;		// ȭ�鿡 ������ URL(�α��� ȭ��)
	
	public CustomAuthenticationFailureHandler(String loginidname, String loginpasswdname, String loginredirectname, String exceptionmsgname, String defaultFailureUrl) {
		this.loginidname = loginidname; 			
		this.loginpasswdname = loginpasswdname;		
		this.loginredirectname = loginredirectname;
		this.exceptionmsgname = exceptionmsgname;	
		this.defaultFailureUrl = defaultFailureUrl;
	}
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {	
		// Request ��ü�� Attribute�� ����ڰ� ���н� �Է��ߴ� �α��� ID�� ��й�ȣ�� �����صξ� �α��� ���������� �̸� �����ϵ��� �Ѵ�
		
		String loginid = request.getParameter(loginidname);
		String loginpasswd = request.getParameter(loginpasswdname);
		String loginRedirect = request.getParameter(loginredirectname);
		
		request.setAttribute(loginidname, loginid);
		request.setAttribute(loginpasswdname, loginpasswd);
		request.setAttribute(loginredirectname, loginRedirect);
		
		
		// Request ��ü�� Attribute�� ���� �޽��� ����
		request.setAttribute(exceptionmsgname, exception.getMessage());
		
		request.getRequestDispatcher(defaultFailureUrl).forward(request, response);
	}
}