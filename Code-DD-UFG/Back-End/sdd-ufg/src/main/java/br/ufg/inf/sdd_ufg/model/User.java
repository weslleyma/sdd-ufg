package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;


public class User extends Entity<User> {

	private String userName;
    private String password;
    private String email;
    
    @Column(name="USERNAME")
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column(name="PASSWORD")
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column(name="EMAIL")
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
}
