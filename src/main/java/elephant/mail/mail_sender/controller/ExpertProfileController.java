package elephant.mail.mail_sender.controller;

import elephant.mail.mail_sender.model.Category;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.model.Skill;
import elephant.mail.mail_sender.service.CategoryService;
import elephant.mail.mail_sender.service.ExpertService;
import elephant.mail.mail_sender.service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.*;

@Controller
public class ExpertProfileController {
    @Autowired
    private SkillService skillService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private CategoryService categoryService;

//    @RequestMapping(value = "/expertProfile", method = {RequestMethod.GET, RequestMethod.POST})
//    public ModelAndView editSkills(ModelAndView modelAndView, Expert expert) {
//        Category category = new Category();
//        category.setServiceCategoryName("دکوراسیون ساختمان");
//        categoryService.addNewCategory(category);
//        Skill skill = new Skill();
//        skill.setBasePrice(30000);
//        skill.setCategory(category);
//        HashSet<Expert> experts = new HashSet<>();
//        experts.add(expert);
//        skill.setExperts(experts);
//        skill.setTitle("سنگ‌کاری");
//        skill.setShortDescription("اجرای تمامی خدمات سنگ‌کاری ساختمان");
//        skillService.addNewSkill(skill);
//        HashSet<Skill> skills = new HashSet<>();
//        expert.setSkills(skills);
//        expertService.updateExpert(expert);
//        List<Skill> skillsByExpert = skillService.getSkillsByExpert(expert);
//        modelAndView.addObject("expertsSkills", skillsByExpert);
//        modelAndView.setViewName("expertSkills");
//        modelAndView.addObject("expert", expert);
//        return modelAndView;
//    }

    @GetMapping("/expertSkills")
    public ModelAndView displaySkills(ModelAndView modelAndView) {
        modelAndView.setViewName("expertSkills");
        return modelAndView;
    }

    @GetMapping("/addSkill")
    public ModelAndView displayCategories(ModelAndView modelAndView) {
        modelAndView.setViewName("addSkill");
        List<Category> categories = categoryService.getAllCategories();
        Map<Category, String> categoryMap = new HashMap<>();
        categories.forEach(category -> categoryMap.put(category, category.getServiceCategoryName()));
        modelAndView.addObject("categoryMap", categoryMap);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", new Category());
        modelAndView.addObject("skill", new Skill());
        return modelAndView;
    }

    @PostMapping("/availableSkills")
    public ModelAndView displayAvailableSkillsBasedOnCategories(ModelAndView modelAndView, Category category) {
//        category = (Category) modelAndView.getModelMap().getAttribute("category");
        modelAndView.addObject("category", category);
//        modelAndView.addObject("skill", new Skill());
        return modelAndView;
    }

    @PostMapping("/addSkill")
    public ModelAndView displayServices(ModelAndView modelAndView, @ModelAttribute("category") Category category) {
        System.out.println(modelAndView.getModelMap().getAttribute("category"));
        List<Skill> skills = skillService.getSkillsByCategory(category);
        Skill skill = (Skill) modelAndView.getModelMap().getAttribute("skill");
        Objects.requireNonNull(skill).setCategory(category);
        modelAndView.addObject("skill", skill);
        modelAndView.addObject("skills", skills);
        modelAndView.setViewName("addSkill");
        return modelAndView;
    }

    @RequestMapping(value = "/successfulAddSkill", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView submitSkillForExpert(ModelAndView modelAndView, Skill skill, Expert expert) {
        if (Objects.nonNull(skill)) {
            expert.addSkill(skill);
            expertService.updateExpert(expert);

            modelAndView.addObject("newSkill", skill);
            modelAndView.addObject("expertAddingNewSkill", expert);
            modelAndView.setViewName("successfulAddSkill");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/expertProfile", method = RequestMethod.GET)
    public ModelAndView viewExpertProfile(ModelAndView modelAndView) {
        modelAndView.setViewName("expertProfile");
        return modelAndView;
    }
}
