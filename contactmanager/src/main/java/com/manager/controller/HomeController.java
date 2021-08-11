package com.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager.dao.UserRepository;
import com.manager.entities.Contact;
import com.manager.entities.User;

@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	
	@RequestMapping("/home")
	public String home()
	{
		return "home";
	}
	
	@RequestMapping("/register")
	public String register()
	{
		return "register";
	}
	
	@RequestMapping("/about")
	public String about()
	{
		return "about";
	}

}
