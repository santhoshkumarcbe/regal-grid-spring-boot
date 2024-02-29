package com.regalgrid.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.Authorities;
import com.regalgrid.demo.repository.AuthoritiesRepository;

@Service

public class AuthoritiesServiceImpl implements AuthoritiesService {

    @Autowired
    AuthoritiesRepository authoritiesRepository;

    @Override
    public boolean createAuthority(Authorities authorities) {
        List<Authorities> existingAuthorities = authoritiesRepository.findAll();
        if (existingAuthorities.isEmpty()) {
            authoritiesRepository.save(authorities);
            return true;
        }
        return false;
    }

    @Override
    public List<Authorities> getAuthorities() {
        return authoritiesRepository.findAll();
    }

    @Override
    public boolean deleteAuthority() {
        List<Authorities> authorities = authoritiesRepository.findAll();
        if (authorities.isEmpty()) {
            return false;
        }

        authoritiesRepository.deleteAll();
        return true;
    }

    @Override
    public String addAuthority(String userrole, String authority) {
        List<Authorities> authoritiesList = authoritiesRepository.findAll();
        Authorities authorities = authoritiesList.get(0);

        if (userrole.equals("admin")) {
            ArrayList<String> array = authorities.getAdmin();
            if (array.contains(authority)) {
                return authority + " found in " + userrole;
            }
            array.add(authority);
            authorities.setAdmin(array);
            authorities.setId(authorities.getId());
            authoritiesRepository.save(authorities);
            return "updated";
        }

        else if (userrole.equals("dealer")) {
            ArrayList<String> array = authorities.getDealer();
            if (array.contains(authority)) {
                return authority + " found in " + userrole;
            }
            array.add(authority);
            authorities.setDealer(array);
            authoritiesRepository.save(authorities);
            return "updated";
        }

        else if (userrole.equals("customer")) {
            ArrayList<String> array = authorities.getCustomer();
            if (array.contains(authority)) {
                return authority + " found in " + userrole;
            }
            array.add(authority);
            authorities.setCustomer(array);
            authoritiesRepository.save(authorities);
            return "updated";
        }

        else {
            return "userrole not defined";
        }

    }

    @Override
    public String removeAuthority(String userrole, String authority) {
        List<Authorities> authoritiesList = authoritiesRepository.findAll();
        Authorities authorities = authoritiesList.get(0);

        if (userrole.equals("admin")) {
            ArrayList<String> array = authorities.getAdmin();
            if (array.contains(authority)) {
                array.remove(authority);
                authorities.setAdmin(array);
                authorities.setId(authorities.getId());
                authoritiesRepository.save(authorities);
                return "updated";
            }
            return authority + " not found in " + userrole;

        }

        else if (userrole.equals("dealer")) {
            ArrayList<String> array = authorities.getDealer();
            if (array.contains(authority)) {
                array.remove(authority);
                authorities.setDealer(array);
                authoritiesRepository.save(authorities);
                return "updated";
            }
            return authority + " not found in " + userrole;
        }

        else if (userrole.equals("customer")) {
            ArrayList<String> array = authorities.getCustomer();
            if (array.contains(authority)) {
                array.remove(authority);
                authorities.setCustomer(array);
                authoritiesRepository.save(authorities);
                return "updated";
            }
            return authority + " not found in " + userrole;
        }

        else {
            return "userrole not defined";
        }

    }

}
