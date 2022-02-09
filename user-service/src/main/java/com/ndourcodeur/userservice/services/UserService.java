package com.ndourcodeur.userservice.services;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.model.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {

    public User addUser(UserRequest request);
    public User editUser(Long id, UserRequest request);
    public List<User> findAllUsers();
    public User findUser(Long id);
    public List<Car> findAllCarsWithUser(Long userId);
    public List<LapTop> findAllLapTopsWithUser(Long userId);
    public void deleteUser(Long id);
    public User getUsername(String username);
    public User getEmail(String email);
    public Boolean existsById(Long userId);
    public Boolean existsByUsername(String username);
    public Boolean existsByEmail(String email);

    // Feign
    public Car saveCar(Long userId, Car car);
    public Car editCarById(Long userId, Long carId, Car car);
    public void deleteCarById(Long carId);
    public Map<String, Object> getUserAndCars(Long userId);
}
