package com.example.zuzextest.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "house")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class House {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String address;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;

    @JsonManagedReference
    @OneToMany(mappedBy = "house", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();
    public void addUser(User user){
        users.add(user);
    }
}
