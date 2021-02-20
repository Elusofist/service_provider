package elephant.mail.mail_sender.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/", "/mainPage"})
public class MainPageController {
    @GetMapping
    public String displayMainPage() {
        return "mainPage";
    }
}
