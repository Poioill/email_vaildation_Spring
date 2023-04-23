package com.example.demo1_registration.registration;


import com.example.demo1_registration.event.RegistrationCompleteEvent;
import com.example.demo1_registration.registration.token.VerificationToken;
import com.example.demo1_registration.registration.token.VerificationTokenRepository;
import com.example.demo1_registration.user.Usr;
import com.example.demo1_registration.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {
    private final UserService userService;
    private final ApplicationEventPublisher publisher;
    private final VerificationTokenRepository tokenRepository;
    @PostMapping
    public String registerUser(@RequestBody RegistrationRequest registrationRequest, final HttpServletRequest request){
        Usr usr = userService.registerUser(registrationRequest);
        publisher.publishEvent(new RegistrationCompleteEvent(usr, applicationUrl(request)));
        return "Success! Please, check your email to complete your registration";
    }
    @GetMapping("/verifyEmail")
    public String verifyEmail(@RequestParam("token") String token){
        VerificationToken theToken = tokenRepository.findByToken(token);
        if (theToken.getUsr().isEnabled()){
            return "This account has already been verified, please, login.";
        }
        String verificationResult = userService.validateToken(token);
        if (verificationResult.equalsIgnoreCase("valid")){
            return "Email verified successfully. Now you can login to your account";
        }
        return "Invalid verification token";
    }
    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
