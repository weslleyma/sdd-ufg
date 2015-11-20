package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@javax.persistence.Entity
@Table(name = "USR")
public class User extends Entity<User> {

	private String userName;
    private String password;
    private String email;
    private Role role;

	@Column(name="USRNAME", length = 50)
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="PSSWORD", length = 20)
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="EMAIL", length = 70)
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@ManyToOne
    @JoinColumn(name = "ROLE_ID")
    public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
