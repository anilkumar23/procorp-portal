package com.procorp.authentication.config;

import com.procorp.authentication.dao.StudentDao;
import com.procorp.authentication.entities.Student;
import com.procorp.authentication.model.StudentDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class CustomUserDetailsService implements UserDetailsService {

/*	@Autowired
	private StudentDao dao;*/
	@Autowired
	private PasswordEncoder bcryptEncoder;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		List<SimpleGrantedAuthority> roles = null;
//		Student student = dao.findByEmail(email);
//		if(student != null && student.getEmail() != null) {
//			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			roles = Arrays.asList(new SimpleGrantedAuthority("ADMIN"));
			return new User(email, bcryptEncoder.encode("123"), roles);
//		}
//		throw new UsernameNotFoundException("User not found with the email " + email);
	}

}
