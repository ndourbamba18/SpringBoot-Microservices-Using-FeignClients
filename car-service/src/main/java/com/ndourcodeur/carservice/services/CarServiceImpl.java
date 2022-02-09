package com.ndourcodeur.carservice.services;

import com.ndourcodeur.carservice.dto.CarRequest;
import com.ndourcodeur.carservice.entity.Car;
import com.ndourcodeur.carservice.exception.ResourceNotFoundException;
import com.ndourcodeur.carservice.model.ResponseTemplateCarWithUser;
import com.ndourcodeur.carservice.model.User;
import com.ndourcodeur.carservice.repository.CarRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Component
@Transactional
@Slf4j
public class CarServiceImpl implements CarService {

    @Autowired
    RestTemplate restTemplate;

    private final CarRepository carRepository;

    public CarServiceImpl(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @Override
    public Car addCar(CarRequest request) {
        log.info("Inside addCar of CarService");
        Car car = new Car();
        car.setId(request.getId());
        car.setName(request.getName());
        car.setPrice(request.getPrice());
        car.setBrand(request.getBrand());
        car.setRegistrationNumber(UUID.randomUUID().toString());
        car.setIsInStock(request.getIsInStock());
        car.setUserId(request.getUserId());
        return carRepository.save(car);
    }

    @Override
    public Car editCar(Long id, CarRequest request) {
        log.info("Inside editCar of CarService");
        Car car = carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
        car.setId(request.getId());
        car.setName(request.getName());
        car.setPrice(request.getPrice());
        car.setBrand(request.getBrand());
        car.setRegistrationNumber(UUID.randomUUID().toString());
        car.setIsInStock(request.getIsInStock());
        car.setUserId(request.getUserId());
        return carRepository.save(car);
    }

    @Override
    public List<Car> findAllCars() {
        log.info("Inside findAllCars of CarService");
        return carRepository.findAll();
    }

    @Override
    public Car findCar(Long id) {
        log.info("Inside findCar of CarService");
        return carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
    }

    @Override
    public List<Car> findByUserId(Long userId) {
        log.info("Inside findByUserId of CarService");
        return carRepository.findByUserId(userId);
    }

    @Override
    public void deleteCar(Long id) {
        log.info("Inside deleteCar of CarService");
        Car car = carRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException("Car does not exist with ID:"+id));
        carRepository.delete(car);
    }

    @Override
    public ResponseTemplateCarWithUser findCarWithUser(Long carId) {
        log.info("Inside findCarWithUser of CarService");
        ResponseTemplateCarWithUser response = new ResponseTemplateCarWithUser();
        Car car = findCar(carId);
        User user = restTemplate.getForObject("http://localhost:8100/api/v1/users/user-detail/" + car.getUserId(), User.class);
        response.setCar(car);
        response.setUser(user);
        return response;
    }

}
