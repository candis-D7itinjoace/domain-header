package tn.sharing.domainheader.events;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import tn.sharing.domainheader.entities.AppUser;

@Getter
@Setter
public class RegistrationCompleteEvent extends ApplicationEvent {

    private AppUser user;

    private String applicationUrl;

    public RegistrationCompleteEvent(AppUser user , String applicationUrl) {
        super(user);
        this.user = user;
        this.applicationUrl = applicationUrl;

    }
}
