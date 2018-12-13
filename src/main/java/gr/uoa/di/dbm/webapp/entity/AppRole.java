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
public class AppRole implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long roleId;

	@Column(name="role_name")
	private String roleName;

	//bi-directional many-to-one association to AppUser
	@ManyToMany(mappedBy="appRoles", fetch = FetchType.LAZY)
	private List<AppUser> appUsers = new ArrayList<>();

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

	public List<AppUser> getAppUsers() {
		return this.appUsers;
	}

	public void setAppUsers(List<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public AppUser addAppUser(AppUser userRole) {
		getAppUsers().add(userRole);

		return userRole;
	}

	public AppUser removeAppUser(AppUser userRole) {
		getAppUsers().remove(userRole);

		return userRole;
	}

}