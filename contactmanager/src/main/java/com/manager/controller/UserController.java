package com.manager.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.manager.dao.ContactRepository;
import com.manager.dao.UserRepository;
import com.manager.entities.Contact;
import com.manager.entities.User;
import com.manager.util.Message;



@Controller
@RequestMapping("/user")
public class UserController 
{
	@Autowired
	 private UserRepository userRepository;
	
	@Autowired
	private ContactRepository contactRepository;
	
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
     public String processContact(@ModelAttribute Contact contact,@RequestParam("profileImage") MultipartFile multipartFile,Principal principal,HttpSession session)
     {
    	 String name=principal.getName();
    	 
    	 
    	 User user=this.userRepository.getUserByUserName(name);
    	 
    	 
    	 contact.setUser(user);
    	 if(multipartFile.isEmpty())
    	 contact.setImage("defimg.png");
    	 else
    	 contact.setImage(multipartFile.getOriginalFilename());
    	 
    	 try
    	 {
    	 File saveFile= new ClassPathResource("static/img").getFile();
    	 
    	 Path path=Paths.get(saveFile.getAbsolutePath()+File.separator+multipartFile.getOriginalFilename());
    	
    	 Files.copy(multipartFile.getInputStream(),path,StandardCopyOption.REPLACE_EXISTING );
    	 session.setAttribute("message",new Message("Contact added successfully","success"));
    	 }catch (Exception e) {
			System.out.println(e.getMessage());
			session.setAttribute("message",new Message("Contact failed successfully","danger"));
			e.printStackTrace();
		}
    	 
    	 
    	 user.getContacts().add(contact);
    	 
    	 this.userRepository.save(user);
    	 
    	 System.out.println(contact.getName());
    	 return"user/addcontact"; 
     }
     
     @GetMapping("/show-contacts/{page}")
     public String showContacts(@PathVariable("page") Integer page ,Principal principal,Model m)
     {
    	 String username=principal.getName();
    	 User user=this.userRepository.getUserByUserName(username);
    	
    	 Pageable pageable=PageRequest.of(page, 2);
    	 
    	 Page<Contact> contacts=this.contactRepository.findContactsByUser(user.getId(),pageable);
    	 m.addAttribute("contacts", contacts);
    	 m.addAttribute("currentPage", page);
    	 m.addAttribute("totalPages",contacts.getTotalPages());
    	 
 
    	 return "user/show_contacts";
     }
     
     @GetMapping("/contact/{cId}")
     public String showDetails(@PathVariable("cId") Integer cId,Model model,Principal principal)
     {
    	 String username=principal.getName();
    	 User user=this.userRepository.getUserByUserName(username);
    	
    	 
    	 System.out.println("CId"+cId);
    	 Optional<Contact> contactOpt = this.contactRepository.findById(cId);
    	 Contact contact=contactOpt.get();
    	
    	 
    	 
    	 if(user.getId()==contact.getUser().getId())
    	 model.addAttribute("contact", contact);
    		 
    		 
    	 return "user/contact_details";
    	
     }
     
     @GetMapping("/delete/{cId}")
     public String deleteContact(@PathVariable("cId") Integer cId,Model model,Principal principal)
     {
    	 String username=principal.getName();
    	 User user=this.userRepository.getUserByUserName(username);
    	 
    	 Optional<Contact> contactOpt=this.contactRepository.findById(cId);
    	 
    	 Contact contact=contactOpt.get();
    	 
    	 System.out.println("called");
    
    	 this.contactRepository.delete(contact);
    	 
    	 return "redirect:/user/show-contacts/0";
     }
     
     @PostMapping("/update/{cId}")
     public String updateForm(@PathVariable int cId,Model m)
     {
    	 Optional<Contact> contactOpt = this.contactRepository.findById(cId);
    	 Contact contact=contactOpt.get();
    	 
    	 m.addAttribute("contact", contact);
    	 return "user/update_form";
     }
     
     
}
 