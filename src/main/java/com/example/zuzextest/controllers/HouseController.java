package com.example.zuzextest.controllers;

import com.example.zuzextest.entity.House;
import com.example.zuzextest.entity.Owner;
import com.example.zuzextest.entity.User;
import com.example.zuzextest.repository.HouseRepository;
import com.example.zuzextest.repository.OwnerRepository;
import com.example.zuzextest.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/house")
public class HouseController {
    @Autowired
    private HouseRepository houseRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public List<House> getAllHouse(){
        return houseRepository.findAll();
    }
    @GetMapping("/{houseId}")
    public List<User> getAllUserByHouse(@PathVariable Long houseId){
        House house = houseRepository.findById(houseId).get();

        return house.getUsers();
    }
    @PostMapping("/")
    public House createHouse(@RequestBody House house){
        return houseRepository.save(house);
    }
    @PreAuthorize("hasRole('OWNER')")
    @PostMapping("/{houseId}/{userId}")
    public House addUserFromHouse(@PathVariable Long houseId, @PathVariable Long userId){
        House house = houseRepository.findById(houseId).get();
        User user = userRepository.findById(userId).get();
        house.addUser(user);
        user.setHouse(house);
        userRepository.save(user);
        return houseRepository.save(house);
    }

    @PostMapping("/addowner/{houseId}/{ownerId}")
    public Owner addOwnerByHouse(@PathVariable Long houseId, @PathVariable Long ownerId){
        Owner owner = ownerRepository.findById(ownerId).get();
        House house = houseRepository.findById(houseId).get();
        house.setOwner(owner);
        owner.addHouse(house);
        houseRepository.save(house);
        return ownerRepository.save(owner);
    }
}
