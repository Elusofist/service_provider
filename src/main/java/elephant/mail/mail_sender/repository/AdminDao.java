package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.Admin;
import elephant.mail.mail_sender.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminDao extends JpaRepository<Admin, Integer> {
    Optional<Admin> findByFirstName(String name);

    Optional<Admin> findByEmailAndPassword(String email, String password);

    Optional<Admin> findByEmail(String email);
}
