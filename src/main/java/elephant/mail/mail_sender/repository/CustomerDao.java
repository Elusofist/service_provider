package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.Customer;
import elephant.mail.mail_sender.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerDao extends JpaRepository<Customer, Integer> {
    Optional<Customer> findByEmail(String email);

    Optional<Customer> findByEmailIgnoreCase(String email);

    Optional<Customer> findByEmailAndPassword(String email, String password);
}
