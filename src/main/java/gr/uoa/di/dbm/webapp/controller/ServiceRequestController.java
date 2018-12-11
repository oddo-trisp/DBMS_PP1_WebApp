package gr.uoa.di.dbm.webapp.controller;

import gr.uoa.di.dbm.webapp.entity.GarbageCart;
import gr.uoa.di.dbm.webapp.entity.Location;
import gr.uoa.di.dbm.webapp.entity.LocationPK;
import gr.uoa.di.dbm.webapp.entity.ServiceRequest;
import gr.uoa.di.dbm.webapp.service.ServiceRequestServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.thymeleaf.util.ArrayUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ServiceRequestController {

    private static final String SERVICE_REQ = "serviceRequest";
    private static final String SERVICE_REQ_TYPES = "serviceRequestTypes";
    private static final String INSERT = "insert";
    private static final String SEARCH = "search";
    private static final String SP_RESULTS = "spResults";
    private static final String TITLE = "title";


    private final ServiceRequestServiceImpl serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestServiceImpl serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @RequestMapping(value = "/insert", method = RequestMethod.GET)
    public String insertPage(Model model) {
        ServiceRequest serviceRequest = new ServiceRequest();
        List serviceRequestTypes = serviceRequestService.findServiceRequestTypes();
        model.addAttribute(SERVICE_REQ, serviceRequest);
        model.addAttribute(SERVICE_REQ_TYPES, serviceRequestTypes);
        return INSERT;
    }

    @RequestMapping(value = "/insert/addRequest", method = RequestMethod.POST)
    public String addRequest(@ModelAttribute ServiceRequest serviceRequest,
                             BindingResult errors, Model model, HttpServletRequest request){
        System.out.println("mpika re mouni");

        Map<String, String> restParameters = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("carts_delivered","").contains(e.getKey()))
                .filter(e -> !ArrayUtils.isEmpty(e.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue()[0]));

        if(!StringUtils.isEmpty(restParameters.get("carts_delivered"))) {
            ServiceRequest previousRequest = serviceRequest;
            serviceRequest = new GarbageCart();
            BeanUtils.copyProperties(previousRequest,serviceRequest);
        }
        //Location location = serviceRequestService.searchLocationById(new Double(37.983809), new Double(23.727530));
        //if(location == null)    location = new Location();
        Location location = new Location();
        location.setId(new LocationPK(new Double(37.983808), new Double(23.727530)));
        //TODO add rest location fields
        location = serviceRequestService.insertOrUpdateLocation(location);
        serviceRequest.setLocation(location);
        serviceRequestService.insertServiceRequest(serviceRequest);
        return "add";
    }

    @RequestMapping(value="/search/{stype}", method = RequestMethod.POST)
    public String search(Model model, @PathVariable String stype,
                         @RequestParam(name="zipcode", required=false)String zipcode,
                         @RequestParam(name="address",required=false)String address,
                         RedirectAttributes ra){
        List results = stype.equals("zip") ? serviceRequestService.searchByZip(zipcode)
                : serviceRequestService.searchByAddress(address);
        ra.addFlashAttribute("resultsList", results);
        model.addAttribute(TITLE, "Search");
        return "redirect:/search";
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchPage(Model model, @ModelAttribute("resultsList") final ArrayList resList){
        List L = new ArrayList();
        if(!resList.isEmpty())
            L = resList;
        model.addAttribute("rList", L);
        model.addAttribute(TITLE, "Search");
        return SEARCH;
    }

    @RequestMapping(value="/spResults/{procnum}", method = RequestMethod.POST)
    public String storedProc(@RequestParam(name="sdate")String sdate, @RequestParam(name="edate", required=false)String edate,
                             @RequestParam(name="reqtype",required=false)String reqtype, RedirectAttributes ra,
                             @PathVariable String procnum) {
        List L = new ArrayList();
        int x = 0;
        if(procnum.equals("1")){
            L = serviceRequestService.storedProcedure1(sdate,edate);
            x = 1;
        }
        if(procnum.equals("2")){
            L = serviceRequestService.storedProcedure2(sdate,edate,reqtype);
            x = 2;
        }
        if(procnum.equals("3")){
            L = serviceRequestService.storedProcedure3(sdate);
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

}
