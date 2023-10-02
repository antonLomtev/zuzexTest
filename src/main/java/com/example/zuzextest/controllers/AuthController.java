package com.example.zuzextest.controllers;

import com.example.zuzextest.entity.Role;
import com.example.zuzextest.entity.RoleName;
import com.example.zuzextest.entity.User;
import com.example.zuzextest.payload.request.LoginRequest;
import com.example.zuzextest.payload.request.SignUpRequest;
import com.example.zuzextest.payload.response.JwtResponse;
import com.example.zuzextest.repository.OwnerRepository;
import com.example.zuzextest.repository.RoleRepository;
import com.example.zuzextest.repository.UserRepository;
import com.example.zuzextest.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtProvider jwtProvider;


    @PostMapping("/signin")
    public ResponseEntity authUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getName(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateJwtToken(authentication);
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(jwt,userDetails.getUsername(), userDetails.getAuthorities()));
    }
    @PostMapping("/signup")
    public ResponseEntity registerUser(@RequestBody SignUpRequest signUpRequest){
        if(userRepository.existsByName(signUpRequest.getName())){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        User user = new User(signUpRequest.getName(), encoder.encode(signUpRequest.getPassword()));
        Set<String> strRole = signUpRequest.getRole();
        Set<Role> roles =new HashSet<>();
        strRole.forEach(role -> {
            switch (role) {
                case "user":
                    Role userRol = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("Role not found"));
                    roles.add(userRol);
                    break;
                case "owner":
                    Role owner = roleRepository.findByName(RoleName.ROLE_OWNER)
                            .orElseThrow(()-> new RuntimeException("Role not found"));
                    roles.add(owner);
                    break;
                default:
                    Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                            .orElseThrow(()-> new RuntimeException("Role not found"));
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        return ResponseEntity.ok().body("User registered successfully");
    }
}
