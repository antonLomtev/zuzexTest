package com.example.zuzextest.repository;

import com.example.zuzextest.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseRepository extends JpaRepository<House, Long> {
}
