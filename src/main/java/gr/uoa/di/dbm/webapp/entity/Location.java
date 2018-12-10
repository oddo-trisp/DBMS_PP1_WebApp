package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;

//import com.vividsolutions.jts.geom.Point;
import org.postgresql.geometric.PGpoint;

/**
 * The persistent class for the location database table.
 * 
 */
@Entity
public class Location implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocationPK id;

	@Column(name="address")
	private String address;

	@Column(name="community_area")
	private String communityArea;

	/*@Column(name="coordinates", columnDefinition="Point")
	private PGpoint coordinates;*/

	@Column(name="location_json")
	private String locationJson;

	@Column(name="police_district")
	private String policeDistrict;

	@Column(name = "ssa")
	private String ssa;

	@Column(name = "ward")
	private String ward;

	@Column(name="zip_code")
	private String zipCode;

	//bi-directional many-to-one association to ServiceRequest
	@OneToMany(mappedBy="location")
	private List<ServiceRequest> serviceRequests;

	public Location() {
	}

	public LocationPK getId() {
		return this.id;
	}

	public void setId(LocationPK id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCommunityArea() {
		return this.communityArea;
	}

	public void setCommunityArea(String communityArea) {
		this.communityArea = communityArea;
	}

	/*public PGpoint getCoordinates() {
		return this.coordinates;
	}

	public void setCoordinates(PGpoint coordinates) {
		this.coordinates = coordinates;
	}*/

	public String getLocationJson() {
		return this.locationJson;
	}

	public void setLocationJson(String locationJson) {
		this.locationJson = locationJson;
	}

	public String getPoliceDistrict() {
		return this.policeDistrict;
	}

	public void setPoliceDistrict(String policeDistrict) {
		this.policeDistrict = policeDistrict;
	}

	public String getSsa() {
		return this.ssa;
	}

	public void setSsa(String ssa) {
		this.ssa = ssa;
	}

	public String getWard() {
		return this.ward;
	}

	public void setWard(String ward) {
		this.ward = ward;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public List<ServiceRequest> getServiceRequests() {
		return this.serviceRequests;
	}

	public void setServiceRequests(List<ServiceRequest> serviceRequests) {
		this.serviceRequests = serviceRequests;
	}

	public ServiceRequest addServiceRequest(ServiceRequest serviceRequest) {
		getServiceRequests().add(serviceRequest);
		serviceRequest.setLocation(this);

		return serviceRequest;
	}

	public ServiceRequest removeServiceRequest(ServiceRequest serviceRequest) {
		getServiceRequests().remove(serviceRequest);
		serviceRequest.setLocation(null);

		return serviceRequest;
	}

}