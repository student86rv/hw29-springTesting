package ua.epam.springapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {

    @GetMapping("/")
    public ModelAndView getMainPage() {
        return new ModelAndView("index");
    }

    @GetMapping("/admin")
    public ModelAndView getAdminPage() {
        return new ModelAndView("admin");
    }

    @GetMapping("/user")
    public ModelAndView getUserPage() {
        return new ModelAndView("user");
    }
}
