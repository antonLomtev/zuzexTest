package com.example.zuzextest.controllers;

import com.example.zuzextest.entity.House;
import com.example.zuzextest.entity.Owner;
import com.example.zuzextest.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/owner")
public class OwnerController {
    @Autowired
    private OwnerRepository ownerRepository;

    @GetMapping("/")
    public List<Owner> getAllOwner(){
        return ownerRepository.findAll();
    }
    @GetMapping("/{ownerId}")
    public List<House> getAllHouseByOwner(@PathVariable Long ownerId){
        return ownerRepository.findById(ownerId).get().getHouse();
    }
    @PostMapping
    public Owner createOwner(@RequestBody Owner owner){
        return ownerRepository.save(owner);
    }
}
