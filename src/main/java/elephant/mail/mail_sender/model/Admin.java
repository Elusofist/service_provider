package elephant.mail.mail_sender.model;

import elephant.mail.mail_sender.enums.Role;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Person {
    public Admin() {
        setRole(Role.ADMIN);
    }
}
