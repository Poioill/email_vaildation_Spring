package com.example.demo1_registration.event;

import com.example.demo1_registration.user.Usr;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {
    private Usr usr;
    private String applicationUrl;

    public RegistrationCompleteEvent(Usr usr, String applicationUrl) {
        super(usr);
        this.usr = usr;
        this.applicationUrl = applicationUrl;
    }
}
