package gr.uoa.di.dbm.webapp.controller;

import gr.uoa.di.dbm.webapp.service.UserDetailsServiceImpl;
import gr.uoa.di.dbm.webapp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute("title", "Welcome");
        model.addAttribute("message", "This is welcome page!");
        return "welcome";
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminPage(Model model, Principal principal) {

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "admin";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Model model) {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String registerPage(Model model) {
        return "register";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPage(HttpServletRequest request) {
        Map<String, String> credentials = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("username", "password").contains(e.getKey()))
                .filter(e -> !ArrayUtils.isEmpty(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue()[0]));
        UserDetails user = userDetailsService.loadUserByUsername(credentials.get("username"));

        return user != null ? "register"
                : userDetailsService.registerUser(credentials) != null
                ? "login" : "register";
    }

    @RequestMapping(value = "/userInfo", method = RequestMethod.GET)
    public String userInfo(Model model, Principal principal) {

        // After user login successfully.
        String userName = principal.getName();

        System.out.println("User Name: " + userName);

        User loginedUser = (User) ((Authentication) principal).getPrincipal();

        String userInfo = WebUtils.toString(loginedUser);
        model.addAttribute("userInfo", userInfo);

        return "userInfo";
    }

    @RequestMapping(value = "/error403", method = RequestMethod.GET)
    public String accessDenied(Model model, Principal principal) {

        if (principal != null) {
            User loginedUser = (User) ((Authentication) principal).getPrincipal();

            String userInfo = WebUtils.toString(loginedUser);

            model.addAttribute("userInfo", userInfo);

            String message = "Hi " + principal.getName() //
                    + "<br> You do not have permission to access this page!";
            model.addAttribute("message", message);

        }

        return "error403";
    }

//    @ResponseBody
//    @RequestMapping(value = "/register/checkUsername")
//    public AjaxResponseBody getSearchResultViaAjax(@RequestBody SearchCriteria search) {
//
//        AjaxResponseBody result = new AjaxResponseBody();
//        //logic
//        return result;
//
//    }
}


