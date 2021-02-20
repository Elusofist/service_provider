package elephant.mail.mail_sender.controller;

import elephant.mail.mail_sender.dto.PersonDto;
import elephant.mail.mail_sender.enums.RegistrationState;
import elephant.mail.mail_sender.enums.Role;
import elephant.mail.mail_sender.model.Admin;
import elephant.mail.mail_sender.model.Customer;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.model.User;
import elephant.mail.mail_sender.service.AdminService;
import elephant.mail.mail_sender.service.CustomerService;
import elephant.mail.mail_sender.service.ExpertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ExpertService expertService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/entry", method = RequestMethod.GET)
    public ModelAndView displayEntryPage(ModelAndView modelAndView, PersonDto person) {
        modelAndView.addObject("person", person);
        modelAndView.setViewName("entry");
        return modelAndView;
    }

    @RequestMapping(value = "/entry", method = RequestMethod.POST)
    public ModelAndView checkRole(PersonDto person, ModelAndView modelAndView) {
        if (person.getRole().equals(Role.CUSTOMER)) {
            setCustomerModel(modelAndView, person);
        } else if (person.getRole().equals(Role.ADMIN)) {
            setAdminModel(modelAndView, person);
        } else {
            setExpertModel(modelAndView, person);
        }
        return modelAndView;
    }

    private void setAdminModel(ModelAndView modelAndView, PersonDto person) {
        Optional<Admin> admin = adminService.findByEmail(person.getEmail());
        if (admin.isPresent() /*&&
                passwordEncoder.matches(person.getPassword(), admin.get().getPassword())*/
        && person.getPassword().equals(admin.get().getPassword())) {
            modelAndView.addObject("admin", admin.get());
            modelAndView.setViewName("adminProfile");
        } else setNotFoundErrorModel(modelAndView);
    }

    private void setCustomerModel(ModelAndView modelAndView, PersonDto person) {
        Optional<Customer> customer = customerService.findByEmailIgnoreCase(person.getEmail());
        if (customer.isPresent()) {
            if (passwordEncoder.matches(person.getPassword(), customer.get().getPassword())) {
                if (customer.get().isEnabled() &&
                        customer.get().getRegistrationState().equals(RegistrationState.CONFIRMED)) {
                    modelAndView.addObject("customer", customer.get());
                    modelAndView.setViewName("customerProfile");
                } else setUserError(modelAndView, customer.get());
            } else setUnmatchedPasswordWithEmailModel(modelAndView);
        } else setNotFoundErrorModel(modelAndView);
    }

    private void setUserError(ModelAndView modelAndView, User user) {
        if (!user.isEnabled()) {
            setUnableErrorModel(modelAndView);
        } else if (user.getRegistrationState().equals(RegistrationState.WAITING)) {
            setWaitingStateError(modelAndView);
        }
    }

    private void setExpertModel(ModelAndView modelAndView, PersonDto person) {
        Optional<Expert> expert = expertService.findByEmailIgnoreCase(
                person.getEmail());
        if (expert.isPresent()) {
            if (passwordEncoder.matches(person.getPassword(), expert.get().getPassword())) {
                if (expert.get().isEnabled() &&
                        expert.get().getRegistrationState().equals(RegistrationState.CONFIRMED)) {
                    modelAndView.addObject("expert", expert.get());
                    modelAndView.setViewName("expertProfile");
                } else setUserError(modelAndView, expert.get());
            } else setUnmatchedPasswordWithEmailModel(modelAndView);
        } else setNotFoundErrorModel(modelAndView);
    }

    private void setUnmatchedPasswordWithEmailModel(ModelAndView model) {
        model.addObject("message", "رمز عبور و ایمیل با هم مطابقت ندارند!");
        model.setViewName("error");
    }

    private void setNotFoundErrorModel(ModelAndView modelAndView) {
        modelAndView.addObject("message", "کاربری با این مشخصات یافت نشد!");
        modelAndView.setViewName("error");
    }

    private void setUnableErrorModel(ModelAndView modelAndView) {
        modelAndView.addObject("message", "شما هنوز فعالسازی نکرده‌ايد!");
        modelAndView.setViewName("error");
    }

    private void setWaitingStateError(ModelAndView modelAndView) {
        modelAndView.addObject("message", "مدير سايت هنوز شما را تأييد نكرده است!");
        modelAndView.setViewName("error");
    }


}