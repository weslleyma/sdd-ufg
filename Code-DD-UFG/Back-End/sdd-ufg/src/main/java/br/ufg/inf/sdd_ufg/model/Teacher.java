package br.ufg.inf.sdd_ufg.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@javax.persistence.Entity
@Table(name = "TEACHER")
public class Teacher extends Entity<Teacher> {

	private User user;
    private List<KnowledgeLevel> knowledgeLevels;
    private String name;
    private String registry;
    private String urlLattes;
    private Date entryDate;
    private String formation;
    private Integer workload;
    private String about;
    private String rg;
    private String cpf;
    private Date birthDate;
    
    @OneToOne
    @JoinColumn(name = "USER_ID")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="teacher", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<KnowledgeLevel> getKnowledgeLevels() {
		return knowledgeLevels;
	}
	
	public void setKnowledgeLevels(List<KnowledgeLevel> knowledgeLevels) {
		this.knowledgeLevels = knowledgeLevels;
	}
	
	@Column(name = "NAME")
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "REGISTRY")
	public String getRegistry() {
		return registry;
	}
	
	public void setRegistry(String registry) {
		this.registry = registry;
	}
	
	@Column(name = "URL_LATTES")
	public String getUrlLattes() {
		return urlLattes;
	}
	
	public void setUrlLattes(String urlLattes) {
		this.urlLattes = urlLattes;
	}
	
	@Column(name = "ENTRY_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.DateUserType")
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@Column(name = "FORMATION")
	public String getFormation() {
		return formation;
	}
	
	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	@Column(name = "WORKLOAD")
	public Integer getWorkload() {
		return workload;
	}
	
	public void setWorkload(Integer workload) {
		this.workload = workload;
	}
	
	@Column(name = "ABOUT")
	public String getAbout() {
		return about;
	}
	
	public void setAbout(String about) {
		this.about = about;
	}
	
	@Column(name = "RG")
	public String getRg() {
		return rg;
	}
	
	public void setRg(String rg) {
		this.rg = rg;
	}
	
	@Column(name = "CPF")
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	@Column(name = "BIRTH_DATE")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.DateUserType")
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
    
   
}
