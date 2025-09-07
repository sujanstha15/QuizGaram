package com.quizpadham.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.quizpadham.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //find user by email
    Optional<User> findByEmail(String email);

    //find user by username
    Optional<User> findByUsername(String username);

    //check if email already exists
    Boolean existsByEmail(String email);

    //check if username already exists
    Boolean existsByUsername(String username);
}
