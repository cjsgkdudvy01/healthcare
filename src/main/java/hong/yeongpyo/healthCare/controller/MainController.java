package hong.yeongpyo.healthCare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	@RequestMapping("/myPage")
	public String main() {
		
		return "myPage";
	}
	@RequestMapping("/login")
	public String mainasd() {
		
		return "login";
	}
}
