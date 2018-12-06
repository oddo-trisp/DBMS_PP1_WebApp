package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the location database table.
 * 
 */
@Embeddable
public class LocationPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private Double latitude;

	private Double longitude;

	public LocationPK() {
	}
	public Double getLatitude() {
		return this.latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return this.longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocationPK)) {
			return false;
		}
		LocationPK castOther = (LocationPK)other;
		return 
			(this.latitude == castOther.latitude)
			&& (this.longitude == castOther.longitude);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (Double.doubleToLongBits(this.latitude) ^ (Double.doubleToLongBits(this.latitude) >>> 32)));
		hash = hash * prime + ((int) (Double.doubleToLongBits(this.longitude) ^ (Double.doubleToLongBits(this.longitude) >>> 32)));
		
		return hash;
	}
}