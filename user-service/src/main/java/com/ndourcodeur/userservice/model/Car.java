package com.ndourcodeur.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Car {

    private Long id;
    private String name;
    private Double price;
    private String brand;
    private String registrationNumber;
    private Boolean isInStock;
    private Long userId;
}
