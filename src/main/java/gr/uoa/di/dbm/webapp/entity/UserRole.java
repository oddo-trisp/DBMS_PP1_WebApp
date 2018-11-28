package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the user_role database table.
 * 
 */
@Entity
@Table(name="user_role")
@NamedQuery(name="UserRole.findAll", query="SELECT u FROM UserRole u")
public class UserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	//bi-directional many-to-one association to AppRole
	@ManyToOne
	@JoinColumn(name="role_id")
	private AppRole appRole;

	//bi-directional many-to-one association to AppUser
	@ManyToOne
	@JoinColumn(name="user_id")
	private AppUser appUser;

	public UserRole() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public AppRole getAppRole() {
		return this.appRole;
	}

	public void setAppRole(AppRole appRole) {
		this.appRole = appRole;
	}

	public AppUser getAppUser() {
		return this.appUser;
	}

	public void setAppUser(AppUser appUser) {
		this.appUser = appUser;
	}

}