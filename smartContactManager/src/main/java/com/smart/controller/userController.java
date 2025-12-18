package com.smart.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class userController {
	
	@RequestMapping("/index")
	public String dashboard() {
		return "normal/user_dashboard";
	}
}
