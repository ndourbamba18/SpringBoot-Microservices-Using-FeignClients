package com.ndourcodeur.carservice.model;

import com.ndourcodeur.carservice.entity.Car;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateCarWithUser {

    private Car car;
    private User user;
}
