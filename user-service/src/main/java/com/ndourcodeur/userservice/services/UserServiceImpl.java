package com.ndourcodeur.userservice.services;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.exception.ResourceNotFoundException;
import com.ndourcodeur.userservice.feignClients.CarFeignClient;
import com.ndourcodeur.userservice.feignClients.LapTopFeignClient;
import com.ndourcodeur.userservice.model.*;
import com.ndourcodeur.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Transactional
@Slf4j
public class UserServiceImpl implements UserService{

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private CarFeignClient carFeignClient;

    @Autowired
    private LapTopFeignClient lapTopFeignClient;

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User addUser(UserRequest request) {
        log.info("Inside addUser of UserService");
        User user = new User();
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User editUser(Long id, UserRequest request) {
        log.info("Inside editUser of UserService");
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
        user.setId(request.getId());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        return userRepository.save(user);
    }

    @Override
    public List<User> findAllUsers() {
        log.info("Inside findAllUsers of UserService");
        return userRepository.findAll();
    }

    @Override
    public User findUser(Long id) {
        log.info("Inside findUser of UserService");
        return userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
    }

    @Override
    public List<Car> findAllCarsWithUser(Long userId) {
        log.info("Inside findAllCarsWithUser of UserService");
        List<Car> cars = restTemplate.getForObject("http://localhost:8200/api/v1/cars/byUserId/" + userId, List.class);
        return cars;
    }

    @Override
    public List<LapTop> findAllLapTopsWithUser(Long userId) {
        log.info("Inside findAllLapTopsWithUser of UserService");
        List<LapTop> lapTops = restTemplate.getForObject("http://localhost:8300/api/v1/lapTops/byUserId/" + userId, List.class);
        return lapTops;
    }

    @Override
    public void deleteUser(Long id) {
        log.info("Inside deleteUser of UserService");
        User existingUser = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("User does not exist with ID:"+id));
        userRepository.delete(existingUser);
    }

    @Override
    public User getUsername(String username) {
        log.info("Inside getUsername of UserService");
        return userRepository.findByUsernameContaining(username);
    }

    @Override
    public User getEmail(String email) {
        log.info("Inside getEmail of UserService");
        return userRepository.findByEmailContaining(email);
    }

    @Override
    public Boolean existsById(Long userId) {
        log.info("Inside existsById of UserService");
        return userRepository.existsById(userId);
    }

    @Override
    public Boolean existsByUsername(String username) {
        log.info("Inside existsByUsername of UserService");
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existsByEmail(String email) {
        log.info("Inside existsByEmail of UserService");
        return userRepository.existsByEmail(email);
    }

    @Override
    public Car saveCar(Long userId, Car car) {
        log.info("Inside saveCar of UserService");
        car.setUserId(userId);
        return carFeignClient.addNewCar(car);
    }

    @Override
    public Car editCarById(Long userId, Long carId, Car car) {
        log.info("Inside editCarById of UserService");
        car.setUserId(userId);
        return carFeignClient.updateCarById(carId, car);
    }

    @Override
    public void deleteCarById(Long carId) {
        log.info("Inside editCarById of UserService");
       // car.setUserId(userId);
        carFeignClient.deleteCarById(carId);
    }

    @Override
    public Map<String, Object> getUserAndCars(Long userId) {
        Map<String, Object> response = new HashMap<>();
        User user = userRepository.findById(userId).orElse(null);
        if (user==null) {
            response.put("message", "User does not exits with ID:" + userId);
            return response;
        }
        response.put("User", user);
        List<Car> cars = carFeignClient.fetchAllCarsByUserId(userId);
        if (cars.isEmpty())
            response.put("Cars", "Sorry, No Almost Content!");
        else
            response.put("Cars", cars);
        // Adding LapTop List
        List<LapTop> lapTops = lapTopFeignClient.fetchAllLapTopsByUserId(userId);
        if (lapTops.isEmpty())
            response.put("LapTops", "Sorry, No Almost Content!");
        else
            response.put("LapTops", lapTops);
        return response;
    }
}
