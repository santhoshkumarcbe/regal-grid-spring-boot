package com.regalgrid.demo.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.model.User;
import com.regalgrid.demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public String addAuthority(String userName,String authority) {
        User user = userRepository.findByUserName(userName);
        if (user==null) {
            return "user not found";
        }
        ArrayList<String> permissions = user.getPermissions();
        if (permissions.contains(authority)) {
            return "authority already given";
        }
        permissions.add(authority);
        user.setUserId(user.getUserId());
        userRepository.save(user);
        return "updated";
    }

    @Override
    public String removeAuthority(String userName, String authority) {
        User user = userRepository.findByUserName(userName);
        if (user==null) {
            return "user not found";
        }
        ArrayList<String> permissions = user.getPermissions();
        if (permissions.contains(authority)) {
            permissions.remove(authority);
            user.setUserId(user.getUserId());
            userRepository.save(user);
            return "updated";
        }
        return "authority not found";
    }

    @Override
    public List<User> getallUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userName) {
        return userRepository.getByUserName(userName);
    }

    @Override
    public double updateWallet(String userName, double amount) {
        System.out.println("userName " + userName);
        User user = userRepository.getByUserName(userName);
        if (user==null) {
            throw new Error("user not found");
        }

        double walletBalance = user.getWallet() + amount;
        walletBalance = formatToTwoDecimal(walletBalance);
        user.setWallet(walletBalance);
        user.setUserId(user.getUserId());
        userRepository.save(user);
        return walletBalance;
    }

    public static double formatToTwoDecimal(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        String formattedNumber = decimalFormat.format(number);
        return Double.parseDouble(formattedNumber);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUserName(username);
    }

    @Override
    public double getWalletBalanceByUsername(String username) {
        User user = userRepository.findByUserName(username);
        return user.getWallet();
    }
   
}
