package br.ufg.inf.sdd_ufg.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@MappedSuperclass()
public abstract class Entity<E extends Entity<E>> {
	
	private Long id;
	@JsonIgnore
    private LocalDateTime createdAt;
	@JsonIgnore
    private LocalDateTime updatedAt;

	@Id
	@Column(name="ID", precision=18, scale=0)
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	@Column(name="CREATED_AT")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateTimeUserType")
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	@JsonIgnore
	@Column(name="UPDATED_AT")
	@Type(type="br.ufg.inf.sdd_ufg.hibernate.type.LocalDateTimeUserType")
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	@SuppressWarnings("unchecked")
	public E consist() {
		return (E) this;
	}
	
	@PrePersist
	protected void preInsert() {
		setCreatedAt(LocalDateTime.now());
	}

	@PreUpdate
	protected void preUpdate() {
		setUpdatedAt(LocalDateTime.now());
	}
	
	@JsonIgnore
	@Transient
	public Boolean getPersistent() {
		return getId() != null && getId().compareTo(0L) > 0;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
			.append("dataInclusao", getCreatedAt())
			.append("dataAlteracao", getUpdatedAt())
			.toString();
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public boolean equals(final Object other) {
		if (this == other)
			return true;
		if (!(this.getClass().isInstance(other)))
			return false;
		return new EqualsBuilder().append(this.getId(), ((Entity<E>) other).getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder().append(this.getId()).toHashCode();
	}
	
	public int compareTo(E o) {
		return new CompareToBuilder().append(this.getId(), ((Entity<E>) o).getId()).toComparison();
	}
}
