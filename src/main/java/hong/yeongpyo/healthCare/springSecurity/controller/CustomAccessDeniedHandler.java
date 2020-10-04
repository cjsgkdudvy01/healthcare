package hong.yeongpyo.healthCare.springSecurity.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	private String errorPage;
	
	public CustomAccessDeniedHandler(String errorPage) {
		if ((errorPage != null) && !errorPage.startsWith("/")) {
            throw new IllegalArgumentException("errorPage must begin with '/'");
		}
		this.errorPage = errorPage;
	}
	
	@Override
	public void handle(HttpServletRequest request,	HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// Ajax�� ���� ���°����� �ľ��Ѵ�
		String ajaxHeader = request.getHeader("X-Ajax-call");
		String result = "";
		
		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		response.setCharacterEncoding("UTF-8");
		
		if(ajaxHeader == null){					// null�� ���� ���� X-Ajax-call ��� ������ ���ٴ� �ǹ��̱� ������ ajax�� �ƴ� �Ϲ����� ������� ���������� �ǹ��Ѵ�
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			Object principal = auth.getPrincipal();
			if (principal instanceof UserDetails) {
				String username = ((UserDetails) principal).getUsername();
				request.setAttribute("username", username);
			}
			request.setAttribute("errormsg", accessDeniedException);
			request.getRequestDispatcher(errorPage).forward(request, response);
		}else{
			if("true".equals(ajaxHeader)){		// true�� ���� �޾Ҵٴ� ���� ajax�� ���������� �ǹ��Ѵ�
				result = "{\"result\" : \"fail\", \"message\" : \"" + accessDeniedException.getMessage() + "\"}";
			}else{								// ��� ������ ������ ���� Ʋ�� ����̹Ƿ� ������� Ʋ�ȴٴ� �ǹ̷� �����ش�
				result = "{\"result\" : \"fail\", \"message\" : \"Access Denied(Header Value Mismatch)\"}";
			}
			response.getWriter().print(result);
			response.getWriter().flush();
		}
	}
}