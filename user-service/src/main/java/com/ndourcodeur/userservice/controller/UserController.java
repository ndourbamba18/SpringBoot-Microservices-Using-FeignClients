package com.ndourcodeur.userservice.controller;

import com.ndourcodeur.userservice.dto.UserRequest;
import com.ndourcodeur.userservice.entity.User;
import com.ndourcodeur.userservice.message.Message;
import com.ndourcodeur.userservice.model.Car;
import com.ndourcodeur.userservice.model.LapTop;
import com.ndourcodeur.userservice.repository.UserRepository;
import com.ndourcodeur.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     *    Adding A single User On The Database
     *
     *    URL ===> http://localhost:8100/api/v1/users/add
     */
    @PostMapping(path = "/add")
    public ResponseEntity<?> addNewUser(@Valid @RequestBody UserRequest request){
        if (userService.existsByUsername(request.getUsername()))
            return new ResponseEntity<>(new Message("Username already exist!"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(request.getEmail()))
            return new ResponseEntity<>(new Message("Email already exist!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.addUser(request), HttpStatus.CREATED);
    }

    /**
     *    Updating User By id From The Database
     *
     *    URL ===> http://localhost:8100/api/v1/users/{idUser}
     */
    @PutMapping(path = "/{idUser}")
    public ResponseEntity<?> updateUserById(@PathVariable Long idUser ,@Valid @RequestBody UserRequest request){
        if (userService.existsByUsername(request.getUsername()) && userService.getUsername(request.getUsername()).getId() != idUser)
            return new ResponseEntity<>(new Message("Username already exist!"), HttpStatus.BAD_REQUEST);
        if (userService.existsByEmail(request.getEmail()) && userService.getEmail(request.getEmail()).getId() != idUser)
            return new ResponseEntity<>(new Message("Email already exist!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.editUser(idUser, request), HttpStatus.CREATED);
    }

    /**
     *    Fetching All Users From The Database
     *
     *    URL ===> http://localhost:8100/api/v1/users/all
     */
    @GetMapping(path = "/all")
    public ResponseEntity<?> fetchAllUsers(){
        List<User> users = userService.findAllUsers();
        if (users.isEmpty())
            return new ResponseEntity<>(new Message("Sorry, No Content Almost!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    /**
     *    Fetching A Single User By id From The Database
     *
     *    URL ===> http://localhost:8100/api/v1/users/user-detail/{idUser}
     */
    @GetMapping(path = "/user-detail/{idUser}")
    public ResponseEntity<?> fetchUserById(@PathVariable Long idUser){
        return new ResponseEntity<>(userService.findUser(idUser), HttpStatus.OK);
    }

    /**
     *    Deleting A Single User By id From The Database
     *
     *    URL ===> http://localhost:8100/api/v1/users/{idUser}
     */
    @DeleteMapping(path = "/{idUser}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long idUser){
        userService.deleteUser(idUser);
        return new ResponseEntity<>(new Message("User deleted successfully with ID:"+idUser), HttpStatus.OK);
    }

    /**
     *    Fetching All Cars By User id From Car Microservice
     *
     *    URL ===> http://localhost:8100/api/v1/users/cars/{userId}
     */
    @GetMapping(path = "/cars/{userId}")
    public ResponseEntity<List<Car>> fetchAllCarsByUserId(@PathVariable Long userId){
        User user = userService.findUser(userId);
        if (user == null)
            return new ResponseEntity(new Message("Sorry, There is no resource almost."), HttpStatus.BAD_REQUEST);
        List<Car> cars = userService.findAllCarsWithUser(userId);
        return new ResponseEntity<>(cars, HttpStatus.OK);
    }

    /**
     *    Fetching All LapTops By User id From LapTop Microservice
     *
     *    URL ===> http://localhost:8100/api/v1/users/lapTops/{userId}
     */
    @GetMapping(path = "/lapTops/{userId}")
    public ResponseEntity<?> fetchAllLapTopsByUserId(@PathVariable Long userId){
        User user = userService.findUser(userId);
        if (user == null)
            return new ResponseEntity<>(new Message("Sorry, There is no resource almost."), HttpStatus.NOT_FOUND);
        List<LapTop> lapTops = userService.findAllLapTopsWithUser(userId);
        return new ResponseEntity<>(lapTops, HttpStatus.OK);
    }

    /**
     *    Adding A New Car On The Database From Car Microservice
     *
     *    URL ===> http://localhost:8100/api/v1/users/addCar/{userId}
     */
    @PostMapping(path = "/addCar/{userId}")
    public ResponseEntity<?> addCar(@PathVariable Long userId, @Valid @RequestBody Car car){
        if (!userService.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:"+userId + "!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.saveCar(userId, car), HttpStatus.CREATED);
    }

    /**
     *    Adding A New Car On The Database From Car Microservice
     *
     *    URL ===> http://localhost:8100/api/v1/users/editCar/{userId}/{carId}
     */
    @PutMapping(path = "/editCar/{userId}/{carId}")
    public ResponseEntity<?> updateCarById(@PathVariable Long userId, @PathVariable Long carId, @Valid @RequestBody Car car){
        if (!userService.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:"+userId + "!"), HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(userService.editCarById(userId, carId, car), HttpStatus.CREATED);
    }

    /**
     *    Adding A New Car On The Database From Car Microservice
     *
     *    URL ===> http://localhost:8100/api/v1/users/deleteCar/{userId}/{carId}
     */
    @DeleteMapping(path = "/deleteCar/{userId}/{carId}")
    public ResponseEntity<?> removeCar(@PathVariable Long userId, @PathVariable Long carId){
        if (!userService.existsById(userId))
            return new ResponseEntity<>(new Message("User does not exist with ID:" + userId+ "!"), HttpStatus.BAD_REQUEST);
        userService.deleteCarById(carId);
        return new ResponseEntity<>(new Message("Car with ID:"+carId+" deleted successfully!"), HttpStatus.OK);
    }

    /**
     *    Fetching All Cars And All LapTops By User id From Car Microservice And LapTop Microservices Using Feign
     *
     *    URL ===> http://localhost:8100/api/v1/users/cars-and-lapTops/{userId}
     */
    @GetMapping(path = "/cars-and-lapTops/{userId}")
    public ResponseEntity<?> getAllCarsAndAllLapTopsByUserId(@PathVariable Long userId){
        Map<String, Object> result = userService.getUserAndCars(userId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
