package elephant.mail.mail_sender.controller;

import elephant.mail.mail_sender.model.ConfirmationToken;
import elephant.mail.mail_sender.model.Customer;
import elephant.mail.mail_sender.repository.ConfirmationTokenDao;
import elephant.mail.mail_sender.service.CustomerService;
import elephant.mail.mail_sender.service.EmailSenderService;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
public class CustomerRegistrationController {
    @Autowired
    private CustomerService customerService;

    @Autowired
    private ConfirmationTokenDao confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView) {
        modelAndView.addObject("customer", new Customer());
        modelAndView.setViewName("register");
        return modelAndView;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, Customer customer) {
        try {
            customerService.addNewCustomer(customer);
            ConfirmationToken confirmationToken = new ConfirmationToken(customer);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(customer.getEmail());
            mailMessage.setSubject("دَر خِدمَت، سرویس شبانه‌روزی خدمات؛ تكميل فرآيند ثبت نام");
            mailMessage.setFrom("<MAIL>");
            mailMessage.setText("لطفاً با كلیک روی لینک مقابل، ثبت نام خود را کامل کنید: "
                    + " http://localhost:8080/confirm-customer-account?token="
                    + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            modelAndView.addObject("user", customer);
            modelAndView.setViewName("successfulRegisteration");
        } catch (BusinessException e) {
            modelAndView.addObject("message", e.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirm-customer-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmCustomerAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            Optional<Customer> customer = customerService.findByEmailIgnoreCase(token.getUser().getEmail());
            if (customer.isPresent()) {
                customer.get().setEnabled(true);
                customerService.updateCustomer(customer.get());
                modelAndView.setViewName("accountVerified");
                modelAndView.addObject("user", customer.get());
            }
        } else {
            modelAndView.addObject("message", "لینک نامعتبر است!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

}
