package com.ndourcodeur.laptopservice.model;

import com.ndourcodeur.laptopservice.entity.Laptop;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplateLapTopWithUser {

    private Laptop laptop;
    private User user;
}
