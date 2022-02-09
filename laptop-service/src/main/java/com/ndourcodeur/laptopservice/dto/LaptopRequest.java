package com.ndourcodeur.laptopservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LaptopRequest {

    private Long id;

    @NotBlank(message = "This field is required!")
    private String lapTopName;

    @Min(0)
    private Double lapTopPrice;

    @NotBlank(message = "This field is required!")
    private String lapTopBrand;

    private Boolean isInStock;

    private String description;

    private Long userId;
}