package elephant.mail.mail_sender.service;

import elephant.mail.mail_sender.model.Category;
import elephant.mail.mail_sender.model.Skill;
import elephant.mail.mail_sender.repository.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryDao categoryDao;

    public List<Category> getAllCategories() {
        return categoryDao.findAll();
    }

    public void addNewCategory(Category category) {
        categoryDao.save(category);
    }
}
