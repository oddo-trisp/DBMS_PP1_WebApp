package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the garbage_cart database table.
 * 
 */
@Entity
@Table(name="garbage_cart")
public class GarbageCart extends ServiceRequest implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="carts_delivered")
	private Long cartsDelivered;

	public GarbageCart() {
	}

	public Long getCartsDelivered() {
		return this.cartsDelivered;
	}

	public void setCartsDelivered(Long cartsDelivered) {
		this.cartsDelivered = cartsDelivered;
	}

}