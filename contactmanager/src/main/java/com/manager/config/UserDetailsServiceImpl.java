package com.manager.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.manager.dao.UserRepository;
import com.manager.entities.User;

public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		User user=userRepository.getUserByUserName(username);
		
		if(user==null)
			throw new UsernameNotFoundException("Nahi mila");
		
		CustomUserDetails cud=new CustomUserDetails(user);
		
		return cud;
	}  

}
