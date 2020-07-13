package hong.yeongpyo.healthCare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/main")
	public String main() {
		
		return "main";
	}
	@RequestMapping("/login.html")
	public String mainasd() {
		
		return "login.html";
	}
}
