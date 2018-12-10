package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the graffity_removal database table.
 * 
 */
@Entity
@Table(name="graffity_removal")
public class GraffityRemoval extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="graffity_location")
	private String graffityLocation;

	@Column(name="surface")
	private String surface;

	public GraffityRemoval() {
	}


	public String getGraffityLocation() {
		return this.graffityLocation;
	}

	public void setGraffityLocation(String graffityLocation) {
		this.graffityLocation = graffityLocation;
	}

	public String getSurface() {
		return this.surface;
	}

	public void setSurface(String surface) {
		this.surface = surface;
	}

}