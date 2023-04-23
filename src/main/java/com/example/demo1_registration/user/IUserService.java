package com.example.demo1_registration.user;

import com.example.demo1_registration.registration.RegistrationRequest;
import com.example.demo1_registration.registration.token.VerificationToken;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<Usr> getUsers();
    Usr registerUser(RegistrationRequest request);
    Optional<Usr> findByEmail(String email);

    void saveUserVerificationToken(Usr theUser, String verificationToken);

    String validateToken(String theToken);
}
