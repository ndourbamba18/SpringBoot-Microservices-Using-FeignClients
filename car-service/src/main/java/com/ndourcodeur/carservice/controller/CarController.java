package com.ndourcodeur.carservice.controller;

import com.ndourcodeur.carservice.dto.CarRequest;
import com.ndourcodeur.carservice.entity.Car;
import com.ndourcodeur.carservice.message.Message;
import com.ndourcodeur.carservice.services.CarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/cars")
public class CarController {

    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    /**
     *    Adding A New Car On The Database
     *
     *    URL ===> http://localhost:8200/api/v1/cars/add
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewCar(@Valid @RequestBody CarRequest request){
        return new ResponseEntity<>(carService.addCar(request), HttpStatus.CREATED);
    }

    /**
     *     Updating A Single Car By id From The Database
     *
     *    URL ===> http://localhost:8200/api/v1/cars/{id}
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<?> updateCarById(@PathVariable Long id ,@Valid @RequestBody CarRequest request){
        return new ResponseEntity<>(carService.editCar(id, request), HttpStatus.CREATED);
    }

    /**
     *      Fetching All Car From The Database
     *
     *    URL ===> http://localhost:8200/api/v1/cars/all
     */
    @GetMapping(path = "/all")
    public ResponseEntity<?> fetchAllCars(){
        List<Car> cars = carService.findAllCars();
        if (cars.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Content Here!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /**
     *      Fetching A Single Car By id From The Database
     *
     *    URL ===> http://localhost:8200/api/v1/cars/{id}
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<?> fetchCarById(@PathVariable Long id){
        return new ResponseEntity<>(carService.findCar(id), HttpStatus.OK);
    }

    /**
     *      Deleting A Single Car By id From The Database
     *
     *    URL ===> http://localhost:8200/api/v1/cars/{id}
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteCarById(@PathVariable Long id){
        carService.deleteCar(id);
        return new ResponseEntity<>(new Message("Car deleted successfully with ID:"+id), HttpStatus.OK);
    }

    /**
     *        Fetching All Cars By User id From User Microservice
     *
     *    URL ===> http://localhost:8200/api/v1/cars/byUserId/{userId}
     */
    @GetMapping(path = "/byUserId/{userId}")
    public ResponseEntity<?> fetchAllCarsByUserId(@PathVariable Long userId){
        List<Car> cars = carService.findByUserId(userId);
        if (cars.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Almost Content!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /**
     *      Fetching Car And User Detail (From User Microservice) By Car id
     *
     *    URL ===> http://localhost:8200/api/v1/cars/detail-car-and-user/{carId}
     */
    @GetMapping(path = "/detail-car-and-user/{carId}")
    public ResponseEntity<?> fetchCarWithUser(@PathVariable Long carId){
        return new ResponseEntity<>(carService.findCarWithUser(carId), HttpStatus.OK);
    }
}
