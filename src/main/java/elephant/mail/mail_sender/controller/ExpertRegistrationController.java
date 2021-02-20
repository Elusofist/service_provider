package elephant.mail.mail_sender.controller;

import elephant.mail.mail_sender.model.ConfirmationToken;
import elephant.mail.mail_sender.model.Expert;
import elephant.mail.mail_sender.repository.ConfirmationTokenDao;
import elephant.mail.mail_sender.service.EmailSenderService;
import elephant.mail.mail_sender.service.ExpertService;
import elephant.mail.mail_sender.service.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Optional;

@Controller
public class ExpertRegistrationController {
    @Autowired
    private ExpertService expertService;

    @Autowired
    private ConfirmationTokenDao confirmationTokenRepository;

    @Autowired
    private EmailSenderService emailSenderService;

    @RequestMapping(value = "/expertRegister", method = RequestMethod.GET)
    public ModelAndView displayRegistration(ModelAndView modelAndView) {
        modelAndView.addObject("expert", new Expert());
        modelAndView.setViewName("expertRegister");
        return modelAndView;
    }

    @RequestMapping(value = "/expertRegister", method = RequestMethod.POST)
    public ModelAndView registerUser(ModelAndView modelAndView, Expert expert) {
        try {
            byte[] encodedImg = Base64.getEncoder().encode(expert.getImage());
            modelAndView.addObject("image", new String(encodedImg, "UTF-8"));
            expertService.addNewExpert(expert);
            ConfirmationToken confirmationToken = new ConfirmationToken(expert);
            confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(expert.getEmail());
            mailMessage.setSubject("دَر خِدمَت، سرویس شبانه‌روزی خدمات؛ تكميل فرآيند ثبت نام");
            mailMessage.setFrom("<MAIL>");
            mailMessage.setText("لطفاً با كلیک روی لینک مقابل، ثبت نام خود را کامل کنید: "
                    + " http://localhost:8080/confirm-expert-account?token="
                    + confirmationToken.getConfirmationToken());

            emailSenderService.sendEmail(mailMessage);
            modelAndView.addObject("user", expert);
            modelAndView.setViewName("successfulRegisteration");
        } catch (BusinessException | UnsupportedEncodingException e) {
            modelAndView.addObject("message", e.getMessage());
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/confirm-expert-account", method = {RequestMethod.GET, RequestMethod.POST})
    public ModelAndView confirmExpertAccount(ModelAndView modelAndView, @RequestParam("token") String confirmationToken) {
        ConfirmationToken token = confirmationTokenRepository.findByConfirmationToken(confirmationToken);
        if (token != null) {
            Optional<Expert> expert = expertService.findByEmailIgnoreCase(token.getUser().getEmail());
            if (expert.isPresent()) {
                expert.get().setEnabled(true);
                expertService.updateExpert(expert.get());
                System.out.println(expert);
                modelAndView.setViewName("accountVerified");
                modelAndView.addObject("user", expert.get());
            }
        } else {
            modelAndView.addObject("message", "لینک نامعتبر است!");
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }
}
