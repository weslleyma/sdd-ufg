package br.ufg.inf.sdd_ufg.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@MappedSuperclass()
public abstract class Entity<E extends Entity<E>> {
	
	private Long id;
//    private LocalDateTime includedAt;
//    private LocalDateTime updatedAt;

	@Id
	@Column(name="ID", precision=18, scale=0)
	public Long getId() {
		if (id == null) synchronized (Entity.class) {
    		try { Thread.sleep(1); } catch (InterruptedException e) {}
    		id = - new Date().getTime();
		}
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@Column(name="INF_DATA_INCLUSAO")
//	@Type(type="br.com.infraestrutura.domain.hibernate.type.LocalDateTimeUserType")
//	public LocalDateTime getIncludedAt() {
//		return includedAt;
//	}
//
//	void setIncludedAt(LocalDateTime includedAt) {
//		this.includedAt = includedAt;
//	}

//	@Column(name="INF_DATA_ALTERACAO")
//	@Type(type="br.com.infraestrutura.domain.hibernate.type.LocalDateTimeUserType")
//	public LocalDateTime getUpdatedAt() {
//		return updatedAt;
//	}

//	void setUpdatedAt(LocalDateTime updatedAt) {
//		this.updatedAt = updatedAt;
//	}

	@SuppressWarnings("unchecked")
	public E consist() {
		return (E) this;
	}
	
	@Transient
	public Boolean getPersistent() {
		return getId() != null && getId().compareTo(0L) > 0;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
			.append("id", getId())
//			.append("dataInclusao", getIncludedAt())
//			.append("dataAlteracao", getUpdatedAt())
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
