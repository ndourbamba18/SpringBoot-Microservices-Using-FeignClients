package com.ndourcodeur.carservice.repository;

import com.ndourcodeur.carservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

    List<Car> findByUserId(Long userId);

    //public Car findByNameContaining(String name);
    //public boolean existsByName(String name);
}
