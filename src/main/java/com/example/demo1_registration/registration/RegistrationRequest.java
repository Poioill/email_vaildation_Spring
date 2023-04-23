package com.example.demo1_registration.registration;

public record RegistrationRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        String role) {
}
