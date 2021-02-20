package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<User, String> {
	User findByEmailIgnoreCase(String emailId);
}
