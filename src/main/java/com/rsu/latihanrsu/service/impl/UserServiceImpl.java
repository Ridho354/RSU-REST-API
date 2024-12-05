package com.rsu.latihanrsu.service.impl;

import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rsu.latihanrsu.entity.UserAccount;
import com.rsu.latihanrsu.repository.UserRepository;
import com.rsu.latihanrsu.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public UserAccount getByUserId(String userId) {
        return userRepository.findById(userId).orElse(null);
    }

    @Override
    public UserAccount getByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Override
    public UserAccount createUser(UserAccount userAccount) {
        return userRepository.saveAndFlush(userAccount);
    }

    @Override
    public UserAccount getByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        // UserAccount userAccount = (UserAccount) authentication.getPrincipal();
        UserAccount userAccount = getByUsername(authentication.getName());
        System.out.println(userAccount);
        return userAccount;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserAccount> user = userRepository.findByUsername(username);
        System.out.println("loadUserByUsername" + user);
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User Not Found"));
    }
}
