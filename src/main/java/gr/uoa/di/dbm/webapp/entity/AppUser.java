package gr.uoa.di.dbm.webapp.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


/**
 * The persistent class for the app_user database table.
 * 
 */
@Entity
@Table(name="app_user")
@NamedQuery(name="AppUser.findByUsername", query="SELECT a FROM AppUser a WHERE a.userName =: userName")

public class AppUser implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	@Column(name = "enabled")
	private Integer enabled;

	@Column(name="encryted_password")
	private String encrytedPassword;

	@Transient
	private  String password;

	@Column(name="user_name")
	private String userName;

	@Column(name="address")
	private String address;

	@Column(name="email")
	private String email;

	//bi-directional many-to-one association to UserRole
	@ManyToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(
			name = "user_role",
			joinColumns = { @JoinColumn(name = "user_id") },
			inverseJoinColumns = { @JoinColumn(name = "role_id") }
	)
	private List<AppRole> appRoles = new ArrayList<>();

	@OneToMany(mappedBy = "appUser")
	private List<AuditLog> auditLogs = new ArrayList<>();

	public AppUser() {
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Integer getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}

	public String getEncrytedPassword() {
		return this.encrytedPassword;
	}

	public void setEncrytedPassword(String encrytedPassword) {
		this.encrytedPassword = encrytedPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<AppRole> getAppRoles() {
		return this.appRoles;
	}

	public void setAppRoles(List<AppRole> userRoles) {
		this.appRoles = appRoles;
	}

	public AppRole addAppRole(AppRole appRole) {
		getAppRoles().add(appRole);

		return appRole;
	}

	public AppRole removeAppRole(AppRole appRole) {
		getAppRoles().remove(appRole);

		return appRole;
	}

	public List<AuditLog> getAuditLogs() {
		return auditLogs;
	}

	public void setAuditLogs(List<AuditLog> auditLogs) {
		this.auditLogs = auditLogs;
	}
}