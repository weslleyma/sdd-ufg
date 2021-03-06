package br.ufg.inf.sdd_ufg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.annotations.Type;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@javax.persistence.Entity
@Table(name = "USR")
public class User extends Entity<User> {

	private String username;
    private String password;
    private String email;
    private Long teacherId;
    private Teacher teacher;
    private Boolean isAdmin;
    private String sessionToken;
    private Date tokenCreatedAt;

	@Column(name="USRNAME", length = 50)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	@JsonIgnore
	@Column(name="PSSWORD", length = 255)
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
	
	@Transient
	@JsonInclude(Include.NON_NULL)
	@JsonProperty("teacher_id")
	public Long getTeacherId() {
		return teacherId;
	}
	
	public void setTeacher(Long teacherId) {
		this.teacherId = teacherId;
	}
	
	@OneToOne
	@JoinColumn(name = "TEACHER_ID")
	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	@JsonProperty("is_admin")
	@Column(name="IS_ADMIN", length=3)
	@Type(type = "br.ufg.inf.sdd_ufg.hibernate.type.BooleanUserType")
	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	@JsonIgnore
	@Column(name="SESSION_TOKEN")
	public String getSessionToken() {
		return sessionToken;
	}

	public void setSessionToken(String sessionToken) {
		this.sessionToken = sessionToken;
	}

	@JsonIgnore
	@Column(name="TOKEN_CREATED_AT")
	@Temporal(value=TemporalType.TIMESTAMP)
	public Date getTokenCreatedAt() {
		return tokenCreatedAt;
	}

	public void setTokenCreatedAt(Date tokenCreatedAt) {
		this.tokenCreatedAt = tokenCreatedAt;
	}
}
