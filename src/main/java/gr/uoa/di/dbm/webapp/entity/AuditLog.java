package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import java.sql.Timestamp;
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
    private Long id;

    @Column(name="action_message")
    private String actionMessage;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName="user_id")
    private AppUser appUser;

    @Column(name="request_id")
    private Long requestId;

    @Column(name="create_date")
    private Timestamp createDate;

    public AuditLog() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public Long getRequestId() {
        return requestId;
    }

    public void setRequestId(Long requestId) {
        this.requestId = requestId;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
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
