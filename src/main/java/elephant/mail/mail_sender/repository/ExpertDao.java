package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.Expert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExpertDao extends JpaRepository<Expert, Integer> {
    Optional<Expert> findByEmail(String email);

    Optional<Expert> findByEmailAndPassword(String email, String password);
}
