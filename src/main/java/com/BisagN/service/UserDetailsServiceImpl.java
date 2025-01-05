package com.BisagN.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BisagN.dao.UserServiceDAO;

import com.BisagN.models.UserLogin;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	UserServiceDAO userService;
	
    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
    	UserLogin appUser = userService.findUser(userName);
        if (appUser == null) {
            System.out.println("User not found! " + userName);
            throw new UsernameNotFoundException("User " + userName + " was not found in the database");
        }
        System.out.println("Found User: " + appUser);
        // [ROLE_USER, ROLE_ADMIN,..]
        List<String> roleNames = userService.getRoleByuserId(String.valueOf(appUser.getUserId()));
        System.out.println("cvdfgb"+roleNames);
        List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();
        if (roleNames.size() > 0) {
            for (String role : roleNames) {
                // ROLE_USER, ROLE_ADMIN,..
                GrantedAuthority authority = new SimpleGrantedAuthority(role);
                grantList.add(authority);
            }
        }
        UserDetails userDetails = (UserDetails) new User(appUser.getUserName(), //
                appUser.getPassword(), grantList);
        return userDetails;
    }
}