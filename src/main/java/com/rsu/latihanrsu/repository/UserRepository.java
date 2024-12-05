package com.rsu.latihanrsu.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.rsu.latihanrsu.entity.UserAccount;

public interface UserRepository extends JpaRepository<UserAccount, String> {
    UserAccount findByUsernameAndPassword(String username, String password);
    Optional<UserAccount> findByUsername(String username);
}
