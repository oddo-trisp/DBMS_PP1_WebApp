package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the trim_trees database table.
 * 
 */
@Entity
@Table(name="trim_trees")
public class TrimTree extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="trees_location")
	private String treesLocation;

	public TrimTree() {
	}

	public String getTreesLocation() {
		return this.treesLocation;
	}

	public void setTreesLocation(String treesLocation) {
		this.treesLocation = treesLocation;
	}

}