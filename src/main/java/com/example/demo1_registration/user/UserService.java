package com.example.demo1_registration.user;

import com.example.demo1_registration.exception.UserAlreadyExistsException;
import com.example.demo1_registration.registration.RegistrationRequest;
import com.example.demo1_registration.registration.token.VerificationToken;
import com.example.demo1_registration.registration.token.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository tokenRepository;
    @Override
    public List<Usr> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public Usr registerUser(RegistrationRequest request) {
        Optional<Usr> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException(
                    "Usr with email " + request.email() + " already exists");
        }
        var newUser = new Usr();
        newUser.setFirstName(request.firstName());
        newUser.setLastName(request.lastName());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }

    @Override
    public Optional<Usr> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(Usr theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        Usr user = token.getUsr();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0) {
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }
}
