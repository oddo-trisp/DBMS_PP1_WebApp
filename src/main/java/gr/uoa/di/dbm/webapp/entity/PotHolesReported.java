package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the pot_holes_reported database table.
 * 
 */
@Entity
@Table(name="pot_holes_reported")
public class PotHolesReported extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="holes_num")
	private Long holesNum;

	public PotHolesReported() {
	}

	public Long getHolesNum() {
		return this.holesNum;
	}

	public void setHolesNum(Long holesNum) {
		this.holesNum = holesNum;
	}

}