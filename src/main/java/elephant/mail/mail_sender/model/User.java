package elephant.mail.mail_sender.model;

import elephant.mail.mail_sender.enums.RegistrationState;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class User extends Person {
    private Boolean enabled;
    @Enumerated(EnumType.STRING)
    private RegistrationState registrationState = RegistrationState.WAITING;

    public User() {
        super();
        this.enabled = false;
    }

    public User(String firstName, String lastName, String email) {
        super(firstName, lastName, email);
        this.enabled = false;
    }

    public RegistrationState getRegistrationState() {
        return registrationState;
    }

    public void setRegistrationState(RegistrationState registrationState) {
        this.registrationState = registrationState;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public Boolean isEnabled() {
        return getEnabled();
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}