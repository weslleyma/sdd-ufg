package br.ufg.inf.sdd_ufg.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@javax.persistence.Entity
@Table(name = "TEACHER")
public class Teacher extends Entity<Teacher> {
    
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
    private List<ClazzIntent> intents = new ArrayList<ClazzIntent>();
    private List<Clazz> clazzes = new ArrayList<Clazz>();
    private List<KnowledgeLevel> knowledgeLevels = new ArrayList<KnowledgeLevel>();
	
	@Column(name = "NAME", length = 100)
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "REGISTRY", length = 30)
	public String getRegistry() {
		return registry;
	}
	
	public void setRegistry(String registry) {
		this.registry = registry;
	}
	
	@JsonProperty("url_lattes")
	@Column(name = "URL_LATTES", length=255)
	public String getUrlLattes() {
		return urlLattes;
	}
	
	public void setUrlLattes(String urlLattes) {
		this.urlLattes = urlLattes;
	}
	
	@JsonProperty("date_entry")
	@Column(name = "ENTRY_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getEntryDate() {
		return entryDate;
	}
	
	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	@Column(name = "FORMATION", length = 30)
	public String getFormation() {
		return formation;
	}
	
	public void setFormation(String formation) {
		this.formation = formation;
	}
	
	@Column(name = "WORKLOAD", length = 3)
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
	
	@JsonProperty("birth_date")
	@Column(name = "BIRTH_DATE")
	@Temporal(value=TemporalType.DATE)
	public Date getBirthDate() {
		return birthDate;
	}
	
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	
	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="teacher", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<ClazzIntent> getIntents() {
		return intents;
	}

	public void setIntents(List<ClazzIntent> intents) {
		this.intents = intents;
	}

	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="teacher", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<Clazz> getClazzes() {
		return clazzes;
	}

	public void setClazzes(List<Clazz> clazzes) {
		this.clazzes = clazzes;
	}

	@JsonProperty("knowledge_levels")
	@JsonInclude(Include.NON_EMPTY)
	@OneToMany( fetch = FetchType.EAGER,  mappedBy="teacher", cascade=CascadeType.ALL, orphanRemoval=true )
	public List<KnowledgeLevel> getKnowledgeLevels() {
		return knowledgeLevels;
	}
	
	public void setKnowledgeLevels(List<KnowledgeLevel> knowledgeLevels) {
		this.knowledgeLevels = knowledgeLevels;
	}
   
}
