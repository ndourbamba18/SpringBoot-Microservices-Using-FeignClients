package com.ndourcodeur.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {

    private Long id;

    @NotBlank(message = "This field is required!")
    private String firstName;

    @NotBlank(message = "This field is required!")
    private String lastName;

    @NotBlank(message = "This field is required!")
    private String username;

    @NotBlank(message = "This field is required!")
    @Email(message = "Please enter a valid email")
    private String email;
}
