package com.manager.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.manager.dao.UserRepository;
import com.manager.entities.User;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	 private UserRepository userRepository;
     @RequestMapping(value="/index", method = RequestMethod.GET)
     public String dashboard(Model model,Principal principal)
     {
    	 String username=principal.getName();
    	 System.out.println(username);
    	 User user=userRepository.getUserByUserName(username);
    	 model.addAttribute("user", user);
    	 return "user/user_dashboard";
     }
}
 