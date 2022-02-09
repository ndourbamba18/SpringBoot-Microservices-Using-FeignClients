package com.ndourcodeur.carservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Car extends DateAudit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "This field is required!")
    private String name;

    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private Double price;

    @NotBlank(message = "This field is required!")
    private String brand;

    private String registrationNumber;

    private Boolean isInStock;

    private Long userId;
}
