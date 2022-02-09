package com.ndourcodeur.userservice.feignClients;

import com.ndourcodeur.userservice.model.Car;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "car-service", url = "http://localhost:8200", path = "/api/v1/cars")
public interface CarFeignClient {

    @PostMapping(path = "/add")
    Car addNewCar(@Valid @RequestBody Car car);

    @PutMapping(path = "/{id}")
    public Car updateCarById(@PathVariable Long id , @Valid @RequestBody Car car);

    @DeleteMapping(path = "/{id}")
    public void deleteCarById(@PathVariable Long id);

    @GetMapping(path = "/byUserId/{userId}")
    public List<Car> fetchAllCarsByUserId(@PathVariable Long userId);

}
