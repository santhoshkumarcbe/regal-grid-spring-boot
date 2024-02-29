package com.regalgrid.demo.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
@Document( collection = "User")

public class User implements UserDetails{
    
    @Id
    private String userId;

    private String emailId;
    
    private String fullName;
    
    private String mobileNumber;
    
    private String passwordHash;
    
    private String userName;

    private int wallet;

    private String userRole;

    private ArrayList<String> permissions;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
    for (String authority : permissions) {
        grantedAuthorities.add(new SimpleGrantedAuthority(authority));
    }
    return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
        
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
        
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
        
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
