package com.smart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import com.smart.helper.Message;

import jakarta.validation.Valid;


@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		User user = new User();
		user.setName("navneet Kumar");
		user.setEmail("nks@gmail.com");
		userRepository.save(user);
		
		return "working";
	}
	
	
	@GetMapping("/")
	public String home(Model model) {
		model.addAttribute("title", "Home - Smart contact manager");
		return "home";
	}
	@GetMapping("/about")
	public String about(Model model) {
		model.addAttribute("title", "About - Smart contact manager");
		return "about";
	}
	
	//handler for register view
	@GetMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title", "Register - Smart contact manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//this handler for registering user
	@PostMapping("/do_register")
	public String registeruser(
			@Valid @ModelAttribute("user") User user,
			BindingResult result,
			@RequestParam(value="agreement",defaultValue = "false") boolean agreement,
			Model model,
			RedirectAttributes redirectAttributes) {
		try {
			if(!agreement) {
				System.out.print("You have not agreed the terms and condition");
				throw new Exception("You have not agreed the terms and condition");
			}
			if(result.hasErrors()) {
				System.out.print("ERROR"+result.toString());
				model.addAttribute("user", user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			System.out.print("Agreement"+agreement);
			System.out.print("User"+user);
			User userData = this.userRepository.save(user);
			model.addAttribute("user", userData);
			redirectAttributes.addFlashAttribute("message", new Message("Successfully registered!!", "alert-success"));
			return "redirect:/signup";

		}catch(Exception e) {
			e.printStackTrace();
			model.addAttribute("user", user);
			redirectAttributes.addFlashAttribute("message", new Message("Something went wrong!!" + e.getMessage(),"alert-danger"));
			return "redirect:/signup";
		}
		
	}
}
