package com.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager.dao.UserRepository;
import com.manager.entities.Contact;
import com.manager.entities.User;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@RequestMapping("/register")
	public String register(Model model)
	{
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}
	
	@RequestMapping("/suc")
	public String succ()
	{
		return "success";
	}
	@RequestMapping(value="/do_register",method = RequestMethod.POST)
	public String doreg(@ModelAttribute("user") User user)
	{
		
		try {
			user.setRole("ROLE_USER");
			user.setEnabled(true);
			user.setImageUrl("default.png");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			User res=this.userRepository.save(user);
			System.out.println(res);
			return "success";
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
			return "failed";
		}
	
	}
	
	@GetMapping("/signin")
	public String customLogin(Model model)
	{
		return "login";
	}

}
