package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tree_debris database table.
 * 
 */
@Entity
@Table(name="tree_debris")
public class TreeDebri extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="debris_location")
	private String debrisLocation;

	public TreeDebri() {
	}

	public String getDebrisLocation() {
		return this.debrisLocation;
	}

	public void setDebrisLocation(String debrisLocation) {
		this.debrisLocation = debrisLocation;
	}

}