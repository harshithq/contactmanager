package com.manager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.manager.dao.UserRepository;
import com.manager.entities.Contact;
import com.manager.entities.User;

@RestController
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/test")
	public String test()
	{
		User user=new User();
		user.setName("Harshit");
		user.setEmail("harshit@innocentrealmex.com");
		Contact con=new Contact();
		user.getContacts().add(con);
		userRepository.save(user);
		return "working";
	}

}
