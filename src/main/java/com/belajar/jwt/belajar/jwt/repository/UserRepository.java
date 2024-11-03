package com.belajar.jwt.belajar.jwt.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.belajar.jwt.belajar.jwt.model.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}

