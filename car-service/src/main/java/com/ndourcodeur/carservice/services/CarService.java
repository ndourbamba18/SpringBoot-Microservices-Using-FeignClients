package com.ndourcodeur.carservice.services;

import com.ndourcodeur.carservice.dto.CarRequest;
import com.ndourcodeur.carservice.entity.Car;
import com.ndourcodeur.carservice.model.ResponseTemplateCarWithUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CarService {

    public Car addCar(CarRequest request);
    public Car editCar(Long id, CarRequest request);
    public List<Car> findAllCars();
    public Car findCar(Long id);
    public List<Car> findByUserId(Long userId);
    public void deleteCar(Long id);
    //public Car getName(String name);
    //public Boolean existsByName(String name);
    public ResponseTemplateCarWithUser findCarWithUser(Long carId);
}
