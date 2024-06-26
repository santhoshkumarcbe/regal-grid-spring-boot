package com.regalgrid.demo.service;

import java.util.List;

import com.regalgrid.demo.model.User;

public interface UserService {

    String addAuthority(String userName, String authority);

    String removeAuthority(String userName, String authority);

    List<User> getallUsers();

    User getUser(String userName);

    double updateWallet(String userName, double amount);

    User getByUsername(String username);

    double getWalletBalanceByUsername(String username);




}
