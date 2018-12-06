package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the rodent_baiting database table.
 * 
 */
@Entity
@Table(name="rodent_baiting")
public class RodentBaiting extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="baited_num")
	private Long baitedNum;

	@Column(name="garbage_num")
	private Long garbageNum;

	@Column(name="rats_num")
	private Long ratsNum;

	public RodentBaiting() {
	}

	public Long getBaitedNum() {
		return this.baitedNum;
	}

	public void setBaitedNum(Long baitedNum) {
		this.baitedNum = baitedNum;
	}

	public Long getGarbageNum() {
		return this.garbageNum;
	}

	public void setGarbageNum(Long garbageNum) {
		this.garbageNum = garbageNum;
	}

	public Long getRatsNum() {
		return this.ratsNum;
	}

	public void setRatsNum(Long ratsNum) {
		this.ratsNum = ratsNum;
	}

}