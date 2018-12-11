package gr.uoa.di.dbm.webapp.entity;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum ServiceRequestType {
    ABANDONED_BUILDINGS("8", "Vacant/Abandoned Building"),
    ABANDONED_VEHICLES("7", "Abandoned Vehicle Complaint"),
    GARBAGE_CART("0", "Garbage Cart Black Maintenance/Replacement"),
    GRAFFITY_REMOVAL("6", "Graffiti Removal"),
    LIGHTS_OUT("11", "Alley Light Out"),
    POT_HOLES("2", "Pot Hole in Street"),
    RODENT_BAITING("5", "Rodent Baiting/Rat Complaint"),
    SANITATION("1", "Sanitation Code Violation"),
    STREETS_LIGHTS_OUT("9", "Street Light Out"),
    STREET_LIGHTS("12", "Street Light - 1/Out"),
    STREET_LIGHTS_ALL("10", "Street Lights - All/Out"),
    TREE_DEBRIS("3", "Tree Debris"),
    TREE_TRIM("4", "Tree Trim");

    private final String key;
    private final String value;

    private static final Map<String, String> lookUpMap = Arrays.stream(ServiceRequestType.values()).collect(Collectors.toMap(ServiceRequestType::getKey,ServiceRequestType::getValue));

    ServiceRequestType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public static String getValueByKey(String key) {
        return lookUpMap.get(key);
    }

}
