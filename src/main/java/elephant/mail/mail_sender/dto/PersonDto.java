package elephant.mail.mail_sender.dto;

import elephant.mail.mail_sender.enums.Role;

public class PersonDto {
    private Role role;
    private String email;
    private String password;

    public PersonDto() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
