package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonDao extends JpaRepository<Person, Integer> {
}
