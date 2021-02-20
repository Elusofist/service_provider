package elephant.mail.mail_sender.repository;

import elephant.mail.mail_sender.model.Category;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.model.Skill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SkillDao extends JpaRepository<Skill, Integer> {
    List<Skill> findByCategory(Category category);

    List<Skill> findAllByExperts(Expert expert);

    void deleteByExperts(Expert expert);
}
