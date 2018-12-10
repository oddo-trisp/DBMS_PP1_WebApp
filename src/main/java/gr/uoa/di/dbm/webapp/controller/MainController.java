package gr.uoa.di.dbm.webapp.controller;

import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import gr.uoa.di.dbm.webapp.service.UserDetailsServiceImpl;
import gr.uoa.di.dbm.webapp.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class MainController {

    private final UserDetailsServiceImpl userDetailsService;
    private static final String ADMIN = "admin";
    private static final String ERROR403 = "error403";
    private static final String INSERT = "insert";
    private static final String LOGIN = "login";
    private static final String MESSAGE = "message";
    private static final String REGISTER = "register";
    private static final String SEARCH = "search";
    private static final String SERVICE_REQUEST = "serviceRequest";
    private static final String SP_RESULTS = "spResults";
    private static final String TITLE = "title";
    private static final String USER_INFO = "userInfo";
    private static final String WELCOME = "welcome";


    @Autowired
    public MainController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
    public String welcomePage(Model model) {
        model.addAttribute(TITLE, "Home");
        model.addAttribute("resultsList","");
        return WELCOME;
    }

    @RequestMapping(value="/search/{stype}", method = RequestMethod.POST)
    public String search(Model model, @PathVariable String stype, RedirectAttributes ra){
        String x = ""; /* Anti gia String x = ""; kane List L = new List() */
        if(stype.equals("zip"))
            x = "zip"; /* edw trexoume to query gia to zip code kai swzoume sthn L anti gia auto pou exw sto x*/
        if(stype.equals("address"))
            x = "address"; /* edw trexoume gia to address kai swzoume sthn L anti gia auto pou exw kanei sto x */
        ra.addFlashAttribute("resultsList", x); // edw pername to L, allakse to x me to L. tipota allo
        model.addAttribute(TITLE, "Search");
        return "redirect:/search";
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchPage(Model model, @ModelAttribute("resultsList") final String resList){
        /* edw allakse sthn panw grammh to 'final String resList' me 'final List resList' kai mhn allakseis tipota allo apla svise to comment */
        model.addAttribute("rList", resList);
        model.addAttribute(TITLE, "Search");
        return SEARCH;
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
        return REGISTER;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registrationPage(HttpServletRequest request) {
        Map<String, String> credentials = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("username", "password").contains(e.getKey()))
                .filter(e -> !ArrayUtils.isEmpty(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue()[0]));

        return userDetailsService.checkIfUserExists(credentials.get("username")) ? REGISTER
                : userDetailsService.registerUser(credentials) != null
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

    @RequestMapping(value="/spResults/{procnum}", method = RequestMethod.POST)
    public String storedProc(@RequestParam(name="sdate")String sdate, @RequestParam(name="edate", required=false)String edate,
                             @RequestParam(name="reqtype",required=false)String reqtype, RedirectAttributes ra,
                             @PathVariable String procnum) {
        List L = new ArrayList();
        int x = 0;
        if(procnum.equals("1")){
            L = userDetailsService.testProc1(sdate,edate);
            x = 1;
        }
        if(procnum.equals("2")){
            L = userDetailsService.testProc2(sdate,edate,reqtype);
            x = 2;
        }
        if(procnum.equals("3")){
            L = userDetailsService.testProc3(sdate);
            x = 3;
        }
        ra.addFlashAttribute("func", x);
        ra.addFlashAttribute("resultsList", L);
        return "redirect:/spResults";
    }

    @RequestMapping(value={"/spResults"}, method = RequestMethod.GET)
    public String testProc(Model model, @ModelAttribute("resultsList") final List resList,
                           @ModelAttribute("func") final int p) {
        model.addAttribute("rList", resList);
        model.addAttribute("fnum", p);
        model.addAttribute(TITLE,"SP Results");
        return SP_RESULTS;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertPage(Model model) {
        ServiceRequest serviceRequest = new ServiceRequest();
        model.addAttribute(SERVICE_REQUEST, serviceRequest);
        return INSERT;
    }
}


