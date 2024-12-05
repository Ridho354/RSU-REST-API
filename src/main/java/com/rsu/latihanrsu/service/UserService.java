package com.rsu.latihanrsu.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService; // dari sini
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.rsu.latihanrsu.entity.UserAccount;

public interface UserService extends UserDetailsService {
    UserAccount getByUserId(String userId);
    UserAccount getByUsername(String username);
    UserAccount createUser(UserAccount userAccount);
    UserAccount getByContext();
    
    @Override
    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}
