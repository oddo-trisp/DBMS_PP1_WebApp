package gr.uoa.di.dbm.webapp.controller;

import gr.uoa.di.dbm.webapp.entity.*;
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
    private static final String SERVICE_REQ_CUR_ACTS = "serviceRequestCurrentActivities";
    private static final String SERVICE_REQ_STATUSES = "serviceRequestStatuses";
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
        List serviceRequestStatuses = serviceRequestService.findServiceRequestStatus();
        List serviceRequestCurrentActivities = serviceRequestService.findServiceRequestCurrentActivity();
        List serviceRequestTypes = Arrays.asList(ServiceRequestType.class.getEnumConstants());

        model.addAttribute(SERVICE_REQ, serviceRequest);
        model.addAttribute(SERVICE_REQ_CUR_ACTS, serviceRequestCurrentActivities);
        model.addAttribute(SERVICE_REQ_STATUSES, serviceRequestStatuses);
        model.addAttribute(SERVICE_REQ_TYPES, serviceRequestTypes);
        model.addAttribute(TITLE, "insert");
        return INSERT;
    }

    @RequestMapping(value = "/insert/addRequest", method = RequestMethod.POST)
    public String addRequest(@ModelAttribute ServiceRequest serviceRequest,
                             BindingResult errors, Model model, HttpServletRequest request){

        Map<String, String> restParameters = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("request_type","building_dangerous","building_fire","building_usage","building_entrance",
                        "building_location_lot","building_open","building_vacant","days_parked","license_plate",
                        "vehicle_color","vehicle_model","graffity_location","surface","baited_num","garbage_num",
                        "rats_num","carts_delivered","nature_violation","holes_number","debris_location","tree_location").contains(e.getKey()))
                .filter(e -> !ArrayUtils.isEmpty(e.getValue()))
                .filter(e -> !StringUtils.isEmpty(e.getValue()[0]))
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue()[0]));

        String requestType = restParameters.getOrDefault("request_type",null);
        ServiceRequest previousRequest = serviceRequest;

        //TODO better error handling
        if(requestType == null)
            return "error";
        else if(ServiceRequestType.GARBAGE_CART.getKey().equals(requestType)) {
            serviceRequest = new GarbageCart();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            Long cartsDelivered = Long.getLong(restParameters.getOrDefault("carts_delivered", null));
            ((GarbageCart) serviceRequest).setCartsDelivered(cartsDelivered);
        }
        else if(ServiceRequestType.SANITATION.getKey().equals(requestType)) {
            serviceRequest = new SanitationCodeComplaint();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            String naturalViolation = restParameters.getOrDefault("nature_violation", null);
            ((SanitationCodeComplaint) serviceRequest).setNatureViolation(naturalViolation);
        }
        else if(ServiceRequestType.POT_HOLES.getKey().equals(requestType)) {
            serviceRequest = new PotHolesReported();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            Long holesNum = Long.getLong(restParameters.getOrDefault("holes_number", null));
            ((PotHolesReported) serviceRequest).setHolesNum(holesNum);
        }
        else if(ServiceRequestType.TREE_DEBRIS.getKey().equals(requestType)) {
            serviceRequest = new TreeDebri();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            String debrisLocation = restParameters.getOrDefault("debris_location", null);
            ((TreeDebri) serviceRequest).setDebrisLocation(debrisLocation);
        }
        else if(ServiceRequestType.TREE_TRIM.getKey().equals(requestType)) {
            serviceRequest = new TrimTree();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            String treesLocation = restParameters.getOrDefault("tree_location", null);
            ((TrimTree) serviceRequest).setTreesLocation(treesLocation);
        }
        else if(ServiceRequestType.RODENT_BAITING.getKey().equals(requestType)) {
            serviceRequest = new RodentBaiting();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            Long baitedNum = Long.getLong(restParameters.getOrDefault("baited_num", null));
            Long garbageNum = Long.getLong(restParameters.getOrDefault("garbage_num", null));
            Long ratsNum = Long.getLong(restParameters.getOrDefault("rats_num", null));

            ((RodentBaiting) serviceRequest).setBaitedNum(baitedNum);
            ((RodentBaiting) serviceRequest).setGarbageNum(garbageNum);
            ((RodentBaiting) serviceRequest).setRatsNum(ratsNum);
        }
        else if(ServiceRequestType.GRAFFITY_REMOVAL.getKey().equals(requestType)) {
            serviceRequest = new GraffityRemoval();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            String graffityLocation = restParameters.getOrDefault("graffity_location", null);
            String surface = restParameters.getOrDefault("surface", null);

            ((GraffityRemoval) serviceRequest).setGraffityLocation(graffityLocation);
            ((GraffityRemoval) serviceRequest).setSurface(surface);
        }
        else if(ServiceRequestType.ABANDONED_VEHICLES.getKey().equals(requestType)) {
            serviceRequest = new AbandonnedVehicle();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            Double daysParked = restParameters.containsKey("days_parked")
                    ? Double.valueOf(restParameters.get("days_parked"))
                    : null;
            String licensePlate = restParameters.getOrDefault("license_plate", null);
            String vehicleColor = restParameters.getOrDefault("vehicle_color", null);
            String vehicleModel = restParameters.getOrDefault("vehicle_model", null);

            ((AbandonnedVehicle) serviceRequest).setDaysParked(daysParked);
            ((AbandonnedVehicle) serviceRequest).setLicensePlate(licensePlate);
            ((AbandonnedVehicle) serviceRequest).setVehicleColor(vehicleColor);
            ((AbandonnedVehicle) serviceRequest).setVehicleModel(vehicleModel);
        }
        else if(ServiceRequestType.ABANDONED_BUILDINGS.getKey().equals(requestType)) {
            serviceRequest = new AbandonnedBuilding();
            BeanUtils.copyProperties(previousRequest,serviceRequest);

            Boolean isBuildingDangerous = restParameters.containsKey("building_dangerous");
            Boolean buildingFire = restParameters.containsKey("building_fire");
            Boolean buildingUsage = restParameters.containsKey("building_usage");
            String buildingEntrance = restParameters.getOrDefault("building_entrance", null);
            String buildingLocationLot = restParameters.getOrDefault("building_location_lot", null);
            String buildingOpen = restParameters.getOrDefault("building_open", null);
            String buildingVacant = restParameters.getOrDefault("building_vacant", null);

            ((AbandonnedBuilding) serviceRequest).setBuildingDangerous(isBuildingDangerous);
            ((AbandonnedBuilding) serviceRequest).setBuildingFire(buildingFire);
            ((AbandonnedBuilding) serviceRequest).setBuildingUsage(buildingUsage);
            ((AbandonnedBuilding) serviceRequest).setBuildingEntrance(buildingEntrance);
            ((AbandonnedBuilding) serviceRequest).setBuildingLocationOnTheLot(buildingLocationLot);
            ((AbandonnedBuilding) serviceRequest).setBuildingOpen(buildingOpen);
            ((AbandonnedBuilding) serviceRequest).setBuildingVacant(buildingVacant);
        }

        Location location = serviceRequestService.insertOrUpdateLocation(serviceRequest.getLocation());
        serviceRequest.setLocation(location);
        serviceRequest.setRequestType(ServiceRequestType.getValueByKey(requestType));
        serviceRequestService.insertServiceRequest(serviceRequest);
        return "addRequest";
    }

    /*private String getNextReqNo(){
        String lastReqNo = serviceRequestService.findLastReqNo();
        String[] parts = lastReqNo.split("(?=-)");
        long prefix = Long.getLong(parts[0]);
        long suffix = Long.getLong(parts[1]);
        long limit = 99999999L;
        prefix = suffix == limit ? prefix++ : prefix;
        suffix = suffix == limit ? 0L : suffix++;
    }*/

    @RequestMapping(value="/searchResults", method = RequestMethod.GET)
    public String searchResults(Model model, @ModelAttribute("resultsList") final ArrayList resList){
        List L = new ArrayList();
        if(!resList.isEmpty())
            L = resList;
        model.addAttribute("rList", L);
        model.addAttribute(TITLE, "Search Results");
        return "searchResults";
    }

    @RequestMapping(value="/search", method = RequestMethod.POST)
    public String search(Model model, HttpServletRequest request, RedirectAttributes ra){

        Map<String, String> parameters = request.getParameterMap()
                .entrySet().stream()
                .filter(e -> Arrays.asList("zipcode","address","reqtype").contains(e.getKey()))
                .filter(e -> !ArrayUtils.isEmpty(e.getValue()))
                .filter(e -> !StringUtils.isEmpty(e.getValue()[0]))
                .collect(Collectors.toMap(Map.Entry::getKey, e->e.getValue()[0]));


        List results = serviceRequestService.search(parameters.getOrDefault("zipcode",null),
                parameters.getOrDefault("address",null),
                parameters.getOrDefault("reqtype",null));
        List L = results.subList(0,250);
        ra.addFlashAttribute("resultsList", L);
        model.addAttribute(TITLE, "Search");
        return "redirect:/searchResults";
    }

    @RequestMapping(value="/search", method = RequestMethod.GET)
    public String searchPage(Model model, @ModelAttribute("resultsList") final ArrayList resList){
        List L = new ArrayList();
        if(!resList.isEmpty())
            L = resList;
        List serviceRequestTypes = Arrays.asList(ServiceRequestType.class.getEnumConstants());
        model.addAttribute("rList", L);
        model.addAttribute(SERVICE_REQ_TYPES, serviceRequestTypes);
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
