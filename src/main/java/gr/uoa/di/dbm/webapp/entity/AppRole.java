package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the app_role database table.
 * 
 */
@Entity
@Table(name="app_role")
@NamedQuery(name="AppRole.findAll", query="SELECT a FROM AppRole a")
public class AppRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long roleId;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-one association to UserRole
	@OneToMany(mappedBy="appRole", cascade=CascadeType.ALL)
	private List<UserRole> userRoles = new ArrayList<>();

	public AppRole() {
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<UserRole> getUserRoles() {
		return this.userRoles;
	}

	public void setUserRoles(List<UserRole> userRoles) {
		this.userRoles = userRoles;
	}

	public UserRole addUserRole(UserRole userRole) {
		getUserRoles().add(userRole);
		userRole.setAppRole(this);

		return userRole;
	}

	public UserRole removeUserRole(UserRole userRole) {
		getUserRoles().remove(userRole);
		userRole.setAppRole(null);

		return userRole;
	}

}