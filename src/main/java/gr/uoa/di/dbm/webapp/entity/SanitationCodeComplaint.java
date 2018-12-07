package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the sanitation_code_complaints database table.
 * 
 */
@Entity
@Table(name="sanitation_code_complaints")
public class SanitationCodeComplaint extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="nature_violation")
	private String natureViolation;

	public SanitationCodeComplaint() {
	}

	public String getNatureViolation() {
		return this.natureViolation;
	}

	public void setNatureViolation(String natureViolation) {
		this.natureViolation = natureViolation;
	}

}