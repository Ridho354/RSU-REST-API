package com.rsu.latihanrsu.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.rsu.latihanrsu.constant.UserRole;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_account")
@Getter
@Setter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
// #SPRING SECURITY# 03 kita buat entity misal UserAccount yang implements UserDetails
public class UserAccount extends BaseEntity implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name =" role")
    private UserRole role = UserRole.ROLE_PATIENT;
    
    // @OneToOne(mappedBy="userAccount")
    // @JsonIgnore
    // private Patient patient;


    // #SPRING SECURITY# 03b kita perlu update method getAuthorities() di entity ini
    // dengan contoh seperti ini List.of(new SimpleGrantedAuthority(role.name()))
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
//    @ManyToOne
//    @JoinColumn(name = "role_id")
//    private Role role;
}
