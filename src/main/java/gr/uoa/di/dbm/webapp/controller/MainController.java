package gr.uoa.di.dbm.webapp.controller;

import gr.uoa.di.dbm.webapp.entity.AppUser;
import gr.uoa.di.dbm.webapp.service.UserDetailsServiceImpl;
import gr.uoa.di.dbm.webapp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

@Controller
public class MainController {

    private final UserDetailsServiceImpl userDetailsService;
    private static final String ADMIN = "admin";
    private static final String ERROR403 = "error403";
    private static final String LOGIN = "login";
    private static final String MESSAGE = "message";
    private static final String REGISTER = "register";
    private static final String TITLE = "title";
    private static final String USER_INFO = "userInfo";
    private static final String WELCOME = "welcome";
    private static final String APP_USER = "appUser";

    @Autowired
    public MainController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute(TITLE, "Home");
        return WELCOME;
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {
        User loggedinUser = (User) ((Authentication) principal).getPrincipal();
        String userInfo = WebUtils.toString(loggedinUser);
        model.addAttribute(USER_INFO, userInfo);
        return ADMIN;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return LOGIN;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        AppUser appUser = new AppUser();
        model.addAttribute(APP_USER, appUser);
        return REGISTER;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPage(@ModelAttribute AppUser appUser) {
        return userDetailsService.checkIfUserExists(appUser.getUserName()) ? REGISTER
                : userDetailsService.registerUser(appUser) != null
                ? LOGIN : REGISTER;
    }

    @RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {
        if (principal != null) {
            User loggedinUser = (User) ((Authentication) principal).getPrincipal();
            String userInfo = WebUtils.toString(loggedinUser);
            model.addAttribute(USER_INFO, userInfo);
            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute(MESSAGE, message);
        }
        return ERROR403;
    }
}


