package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the abandonned_buildings database table.
 * 
 */
@Entity
@Table(name="abandonned_buildings")
public class AbandonnedBuilding extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="building_dangerous")
	private Boolean buildingDangerous;

	@Column(name="building_entrance")
	private String buildingEntrance;

	@Column(name="building_fire")
	private Boolean buildingFire;

	@Column(name="building_location_on_the_lot")
	private String buildingLocationOnTheLot;

	@Column(name="building_open")
	private String buildingOpen;

	@Column(name="building_usage")
	private Boolean buildingUsage;

	@Column(name="building_vacant")
	private String buildingVacant;

	public AbandonnedBuilding() {
	}

	public Boolean getBuildingDangerous() {
		return this.buildingDangerous;
	}

	public void setBuildingDangerous(Boolean buildingDangerous) {
		this.buildingDangerous = buildingDangerous;
	}

	public String getBuildingEntrance() {
		return this.buildingEntrance;
	}

	public void setBuildingEntrance(String buildingEntrance) {
		this.buildingEntrance = buildingEntrance;
	}

	public Boolean getBuildingFire() {
		return this.buildingFire;
	}

	public void setBuildingFire(Boolean buildingFire) {
		this.buildingFire = buildingFire;
	}

	public String getBuildingLocationOnTheLot() {
		return this.buildingLocationOnTheLot;
	}

	public void setBuildingLocationOnTheLot(String buildingLocationOnTheLot) {
		this.buildingLocationOnTheLot = buildingLocationOnTheLot;
	}

	public String getBuildingOpen() {
		return this.buildingOpen;
	}

	public void setBuildingOpen(String buildingOpen) {
		this.buildingOpen = buildingOpen;
	}

	public Boolean getBuildingUsage() {
		return this.buildingUsage;
	}

	public void setBuildingUsage(Boolean buildingUsage) {
		this.buildingUsage = buildingUsage;
	}

	public String getBuildingVacant() {
		return this.buildingVacant;
	}

	public void setBuildingVacant(String buildingVacant) {
		this.buildingVacant = buildingVacant;
	}

}