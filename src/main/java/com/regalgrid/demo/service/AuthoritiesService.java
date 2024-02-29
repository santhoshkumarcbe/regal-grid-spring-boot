package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.model.Authorities;

public interface AuthoritiesService {

    boolean createAuthority(Authorities authorities);

    List<Authorities> getAuthorities();

    boolean deleteAuthority();

    String addAuthority(String userrole, String authority);

    String removeAuthority(String userrole, String authority);
    
}
