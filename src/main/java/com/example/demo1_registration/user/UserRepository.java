package com.example.demo1_registration.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Usr, Long> {
    Optional<Usr> findByEmail(String email);
}
