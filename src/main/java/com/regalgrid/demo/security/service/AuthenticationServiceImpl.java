package com.regalgrid.demo.security.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.regalgrid.demo.exception.DuplicateUserException;
import com.regalgrid.demo.model.Authorities;
import com.regalgrid.demo.model.User;
import com.regalgrid.demo.repository.AuthoritiesRepository;
import com.regalgrid.demo.repository.UserRepository;
import com.regalgrid.demo.security.dto.AuthenticationRequest;
import com.regalgrid.demo.security.dto.AuthenticationResponse;
import com.regalgrid.demo.security.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthenticationServiceImpl implements AuthenticationService {
    
        private final UserRepository userRepository;
        private final AuthoritiesRepository authoritiesRepository;
        private final JwtService jwtService;
        private final PasswordEncoder passwordEncoder;
        private final AuthenticationManager authenticationManager;

        public ResponseEntity<AuthenticationResponse> register(RegisterRequest request) {
                List<Authorities> authoritiesList = authoritiesRepository.findAll();
                Authorities authorities = authoritiesList.get(0);
                ArrayList<String> permissions;
                String userrole = request.getUserRole();
                if (userrole.equals("admin")) {
                        permissions = authorities.getAdmin();
                }
                else if (userrole.equals("dealer")) {
                        permissions = authorities.getDealer();
                }
                else if (userrole.equals("customer")) {
                        permissions = authorities.getCustomer();
                }
                else{
                        throw new Error("Userrole is not valid");
                }

                User user = User.builder()
                                .emailId(request.getEmailId())
                                .fullName(request.getFullName())
                                .mobileNumber(request.getMobileNumber())
                                .userName(request.getUserName())
                                .userRole(request.getUserRole())
                                .passwordHash(passwordEncoder.encode(request.getPasswordHash()))
                                .permissions(permissions)
                                .build();

                User savedUser;
                try {
                        if (userRepository.existsByEmailId(user.getEmailId())) {
                                throw new DuplicateUserException("Email ID already exists.");
                        }
                        else if (userRepository.existsByMobileNumber(user.getMobileNumber())) {
                                throw new DuplicateUserException("Mobile Number already exists.");
                        }
                        else if (userRepository.existsByUserName(request.getUserName())) {
                                throw new DuplicateUserException("User Name already exists.");
                        }

                        else{
                                savedUser = userRepository.save(user);
                        }
                } catch (DuplicateUserException e) {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
                }

                HashMap<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", savedUser.getAuthorities());
                
                String jwtToken = jwtService.generateToken(savedUser);

                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .build(), HttpStatus.OK);
        }

        public ResponseEntity<AuthenticationResponse> authenticate(AuthenticationRequest request) {
                authenticationManager.authenticate(
                                new UsernamePasswordAuthenticationToken(
                                                request.getUserName(),
                                                request.getPassword()));

                User userDetails = userRepository.findByUserName(request.getUserName());

                HashMap<String, Object> extraClaims = new HashMap<>();
                extraClaims.put("Authorities", userDetails.getAuthorities());
                String jwtToken = jwtService.generateToken(extraClaims, userDetails);
                return new ResponseEntity<>(AuthenticationResponse.builder()
                                .token(jwtToken)
                                .user(userDetails)
                                .build(), HttpStatus.OK);
        }

        @Override
        public String updatePassword(String newpassword, String userName) {
                String password = passwordEncoder.encode(newpassword);
                User user = userRepository.findByUserName(userName);
                if (user == null) {
                        return "user name not found";
                }
                user.setPasswordHash(password);
                if(userRepository.save(user) != null) {
                        return "true";
                }
                return "unknown error";
        }

        @Override
        public String getEmailByUserName(String username) {
                User user = userRepository.findByUserName(username);
                if (user == null) {
                        return "error";
                }
                return user.getEmailId();
        }
}
