package elephant.mail.mail_sender;

import elephant.mail.mail_sender.model.Admin;
import elephant.mail.mail_sender.service.AdminService;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailSenderApplication.class, args);
    }

}
