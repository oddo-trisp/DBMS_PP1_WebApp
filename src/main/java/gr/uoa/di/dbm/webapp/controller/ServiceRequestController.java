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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.thymeleaf.util.ArrayUtils;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ServiceRequestController {

    private final ServiceRequestServiceImpl serviceRequestService;

    @Autowired
    public ServiceRequestController(ServiceRequestServiceImpl serviceRequestService) {
        this.serviceRequestService = serviceRequestService;
    }

    @RequestMapping(value = "/insert/addRequest", method = RequestMethod.POST)
    public String addRequest(@ModelAttribute ServiceRequest serviceRequest, BindingResult errors, Model model, HttpServletRequest request){
        System.out.println("mpika re mouni");

        Map<String, String> restParameters = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("carts_delivered").contains(e.getKey()))
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

}
