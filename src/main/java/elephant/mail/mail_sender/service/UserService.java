package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.repository.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;


}
