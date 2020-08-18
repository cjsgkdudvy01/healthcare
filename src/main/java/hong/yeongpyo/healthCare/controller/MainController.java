package hong.yeongpyo.healthCare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import hong.yeongpyo.healthCare.springSecurity.Member;

@Controller
public class MainController {
	@RequestMapping("/myPage")
	public String main() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		Object principal = auth.getPrincipal();
		String name = "";
		if(principal != null&& principal instanceof Member)
			name = ((Member)principal).getName();
		System.out.println("사용자 이름: "+name);
		return "myPage";
	}
	@RequestMapping("/login")
	public String mainasd() {
		
		return "login";
	}
}
