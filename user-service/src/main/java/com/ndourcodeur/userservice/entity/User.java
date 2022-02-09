package com.ndourcodeur.userservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User extends DateAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
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
