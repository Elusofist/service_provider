package elephant.mail.mail_sender.model;

import elephant.mail.mail_sender.enums.Role;

import javax.persistence.Entity;

@Entity
public class Customer extends User {
    public Customer() {
        setRole(Role.CUSTOMER);
    }
}
