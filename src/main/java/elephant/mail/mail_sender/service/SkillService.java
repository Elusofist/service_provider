package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.model.Category;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.model.Skill;
import elephant.mail.mail_sender.repository.SkillDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {
    @Autowired
    private SkillDao skillDao;

    public void addNewSkill(Skill skill) {
        skillDao.save(skill);
    }

    public Skill getSkillById(Integer id) throws Exception {
        Optional<Skill> skill = skillDao.findById(id);
        if (skill.isPresent()) {
            return skill.get();
        }
        throw new Exception("شناسۀ مورد نظر موجود نیست.");
    }

    public List<Skill> getSkillsByCategory(Category category) {
        return skillDao.findByCategory(category);
    }

    public List<Skill> getSkillsByExpert(Expert expert) {
        return skillDao.findAllByExperts(expert);
    }

    public void removeSkill(Skill skill) {
        skillDao.delete(skill);
    }

    public void removeAllSkills(Expert expert) {
        skillDao.deleteByExperts(expert);
    }
}
