package com.ndourcodeur.userservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LapTop {

    private Long id;
    private String lapTopName;
    private Double lapTopPrice;
    private String lapTopBrand;
    private Boolean isInStock;
    private String description;
    private Long userId;
}
