package gr.uoa.di.dbm.webapp.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

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

@NamedStoredProcedureQuery(name = "ServiceRequest.zip_search", procedureName = "zip_search", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "zipCode", type = String.class)
})

@NamedStoredProcedureQuery(name = "ServiceRequest.search", procedureName = "search", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "zip", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "address", type = String.class),
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "type", type = String.class)
})

@NamedStoredProcedureQuery(name = "ServiceRequest.address_search", procedureName = "address_search", parameters = {
		@StoredProcedureParameter(mode = ParameterMode.IN, name = "address", type = String.class)
})

@NamedQuery(name="ServiceRequest.findRequestStatus", query="SELECT DISTINCT r.status FROM ServiceRequest r WHERE r.status IS NOT NULL")

@NamedQuery(name="ServiceRequest.findRequestCurrentActivity", query="SELECT DISTINCT r.currentActivity FROM ServiceRequest r WHERE r.currentActivity IS NOT NULL")

@NamedQuery(name="ServiceRequest.findByZipcode", query="SELECT r FROM ServiceRequest r " +
		"WHERE TYPE(r) = ServiceRequest AND r.location.zipCode =: zipCode")

@NamedQuery(name="ServiceRequest.findByAddress", query="SELECT r FROM ServiceRequest r " +
		"WHERE r.location.address =: address")

@NamedQuery(name="ServiceRequest.findLatestReqNo", query="SELECT r.serviceRequestNo FROM ServiceRequest r " +
        "WHERE r.serviceRequestNo is not null and r.serviceRequestNo like '%-%' order by r.serviceRequestNo DESC")

public class ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="service_request_id")
	private Long serviceRequestId;

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

	public Long getServiceRequestId() {
		return this.serviceRequestId;
	}

	public void setServiceRequestId(Long serviceRequestId) {
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