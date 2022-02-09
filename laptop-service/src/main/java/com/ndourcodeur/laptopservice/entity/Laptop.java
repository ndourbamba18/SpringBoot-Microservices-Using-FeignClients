package com.ndourcodeur.laptopservice.entity;

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
public class Laptop extends DateAudit {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long id;

    @NotBlank(message = "This field is required!")
    private String lapTopName;

    @Min(value = 0, message = "Price must be greater than or equal to 0.")
    private Double lapTopPrice;

    @NotBlank(message = "This field is required!")
    private String lapTopBrand;

    private Boolean isInStock;

    private String description;

    private Long userId;
}
