package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Map;

/**
 * The persistent class for the service_request database table.
 * 
 */
@Entity
@Table(name="service_request")
@Inheritance(strategy = InheritanceType.JOINED)

@NamedStoredProcedureQuery(name = "ServiceRequest.procedure1", procedureName = "procedure1", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "startDate", type = Timestamp.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "endDate", type = Timestamp.class)
})

@NamedStoredProcedureQuery(name = "ServiceRequest.procedure2", procedureName = "procedure2", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "startDate", type = Timestamp.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "endDate", type = Timestamp.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "reqType", type = String.class)
})

@NamedStoredProcedureQuery(name = "ServiceRequest.procedure3", procedureName = "procedure3", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "startDate", type = Timestamp.class)
})

public class ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="service_request_id")
	private Integer serviceRequestId;

	@Column(name="completion_date")
	private Timestamp completionDate;

	@Column(name="create_date")
	private Timestamp createDate;

	@Column(name="current_activity")
	private String currentActivity;

	@Column(name="most_recent_action")
	private String mostRecentAction;

	@Column(name="request_type")
	private String requestType;

	@Column(name="service_request_no")
	private String serviceRequestNo;

	@Column(name="status")
	private String status;

	//bi-directional many-to-one association to Location
	@ManyToOne
	@JoinColumns({
		@JoinColumn(name="latitude", referencedColumnName="latitude"),
		@JoinColumn(name="longitude", referencedColumnName="longitude")
		})
	private Location location;

	public ServiceRequest() {
	}

	public Integer getServiceRequestId() {
		return this.serviceRequestId;
	}

	public void setServiceRequestId(Integer serviceRequestId) {
		this.serviceRequestId = serviceRequestId;
	}

	public Timestamp getCompletionDate() {
		return this.completionDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	public Timestamp getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Timestamp createDate) {
		this.createDate = createDate;
	}

	public String getCurrentActivity() {
		return this.currentActivity;
	}

	public void setCurrentActivity(String currentActivity) {
		this.currentActivity = currentActivity;
	}

	public String getMostRecentAction() {
		return this.mostRecentAction;
	}

	public void setMostRecentAction(String mostRecentAction) {
		this.mostRecentAction = mostRecentAction;
	}

	public String getRequestType() {
		return this.requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getServiceRequestNo() {
		return this.serviceRequestNo;
	}

	public void setServiceRequestNo(String serviceRequestNo) {
		this.serviceRequestNo = serviceRequestNo;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Location getLocation() {
		return this.location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

}