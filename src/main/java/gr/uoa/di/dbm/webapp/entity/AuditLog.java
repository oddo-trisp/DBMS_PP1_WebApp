package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the audit_log database table.
 *
 */
@Entity
@Table(name="audit_log")
@NamedQuery(name="AuditLog.findByUsername", query="SELECT a FROM AuditLog a WHERE a.appUser.userName =: userName")
public class AuditLog implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private Integer id;

    @Column(name="action_message")
    private String actionMessage;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id")
    private AppUser appUser;

    @Column(name="request_id")
    private Integer requestId;

    public AuditLog() {
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public String getActionMessage() {
        return this.actionMessage;
    }

    public void setActionMessage(String actionMessage) {
        this.actionMessage = actionMessage;
    }

    public AppUser getUser() {
        return this.appUser;
    }

    public void setUserId(AppUser appUser) {
        this.appUser = appUser;
    }

}
