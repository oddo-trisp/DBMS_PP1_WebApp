package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the abandonned_vehicles database table.
 * 
 */
@Entity
@Table(name="abandonned_vehicles")
public class AbandonnedVehicle extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="days_parked")
	private double daysParked;

	@Column(name="license_plate")
	private String licensePlate;

	@Column(name="vehicle_color")
	private String vehicleColor;

	@Column(name="vehicle_model")
	private String vehicleModel;

	public AbandonnedVehicle() {
	}

	public double getDaysParked() {
		return this.daysParked;
	}

	public void setDaysParked(double daysParked) {
		this.daysParked = daysParked;
	}

	public String getLicensePlate() {
		return this.licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVehicleColor() {
		return this.vehicleColor;
	}

	public void setVehicleColor(String vehicleColor) {
		this.vehicleColor = vehicleColor;
	}

	public String getVehicleModel() {
		return this.vehicleModel;
	}

	public void setVehicleModel(String vehicleModel) {
		this.vehicleModel = vehicleModel;
	}

}