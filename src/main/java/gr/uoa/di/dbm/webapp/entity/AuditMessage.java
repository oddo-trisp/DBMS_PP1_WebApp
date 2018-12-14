package gr.uoa.di.dbm.webapp.entity;

import javax.persistence.NamedQuery;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@NamedQuery(name="AuditMessage.findByUser", query="SELECT a FROM AuditMessage a WHERE a.appUser.username =: userName")

public enum AuditMessage {
    INSERT("insertServiceRequest", "Insert new incident"),
    SEARCH_SUGG1("storedProcedure1", "Search incident using suggestion 1 with parameters "),
    SEARCH_SUGG2("storedProcedure2", "Search incident using suggestion 2 with parameters "),
    SEARCH_SUGG3("storedProcedure3", "Search incident using suggestion 3 with parameter "),
    SEARCH("search", "Search incident with parameters ");


    private final String key;
    private final String value;

    private static final Map<String, String> lookUpMap = Arrays.stream(AuditMessage.values()).collect(Collectors.toMap(AuditMessage::getKey,AuditMessage::getValue));

    AuditMessage(String key, String value) {
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

