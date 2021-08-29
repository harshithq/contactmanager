package com.manager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manager.dao.UserRepository;
import com.manager.entities.Contact;
import com.manager.entities.User;

@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	 private UserRepository userRepository;
	
	 @ModelAttribute
	 public void addAll(Model model,Principal principal)
	 {
		 String username=principal.getName();
    	 System.out.println(username);
    	 User user=userRepository.getUserByUserName(username);
    	 model.addAttribute("user", user);
	 }
	 
	 //dashboard_home
     @RequestMapping(value="/index", method = RequestMethod.GET)
     public String dashboard(Model model,Principal principal)
     {
    	
    	 return "user/user_dashboard";
     }
     
     @GetMapping("/add-contact")
     public String addContact(Model model)
     {
    	 model.addAttribute("title","Add Contact");
    	 model.addAttribute("contact",new Contact());
    	 return "user/addcontact";
     }
     
     @PostMapping("/process-contact")
     public String processContact(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile multipartFile,Principal principal)
     {
    	 String name=principal.getName();
    	 
    	 
    	 User user=this.userRepository.getUserByUserName(name);
    	 
    	 
    	 contact.setUser(user);
    	 
    	 contact.setImage(multipartFile.getOriginalFilename());
    	 
    	 try
    	 {
    	 File saveFile= new ClassPathResource("static/img").getFile();
    	 
    	 Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename());
    	
    	 Files.copy(multipartFile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING );
    	 }catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
    	 
    	 
    	 user.getContacts().add(contact);
    	 
    	 this.userRepository.save(user);
    	 
    	 System.out.println(contact.getName());
    	 return"user/addcontact";
     }
}
 