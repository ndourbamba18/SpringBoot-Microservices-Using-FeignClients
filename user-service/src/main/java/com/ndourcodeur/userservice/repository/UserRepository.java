package com.ndourcodeur.userservice.repository;

import com.ndourcodeur.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsernameContaining(String username);
    public User findByEmailContaining(String email);

    public boolean existsByUsername(String username);
    public boolean existsByEmail(String email);
}
