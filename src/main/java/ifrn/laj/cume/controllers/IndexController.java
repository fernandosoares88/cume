package ifrn.laj.cume.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	
	@GetMapping("/")
	public String index() {
		return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login/login";
	}
	
	@GetMapping("/logout")
	public String logout() {
		return "login/logout";
	}
	

}
