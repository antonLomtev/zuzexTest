package com.example.zuzextest.controllers;

import com.example.zuzextest.entity.User;
import com.example.zuzextest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @GetMapping("/")
    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    @PostMapping("/")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }
    @PutMapping("/{userId}")
    public User changedUser(@PathVariable Long userId, @RequestBody User changedUser){
        User user = userRepository.findById(userId).get();
        user.setAge(changedUser.getAge());
        user.setName(changedUser.getName());
        return userRepository.save(user);
    }
    @DeleteMapping("/{userId}")
    public void  deleteUser(@PathVariable Long userId){
        userRepository.delete(userRepository.findById(userId).get());
    }
}
