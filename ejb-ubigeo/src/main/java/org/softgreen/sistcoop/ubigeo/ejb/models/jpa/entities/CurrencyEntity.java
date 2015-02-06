package org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="CURRENCY", indexes = { @Index(columnList = "code") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
@NamedQuery(name = CurrencyEntity.findAll, query = "Select c from CurrencyEntity c")
@NamedQueries(value = {
		@NamedQuery(name = CurrencyEntity.findByCode, query = "Select p from CurrencyEntity p WHERE p.code = :code"),
		@NamedQuery(name = CurrencyEntity.findBySimbol, query = "Select p from CurrencyEntity p WHERE p.simbol = :simbol") })
public class CurrencyEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public final static String base = "org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CurrencyEntity";
	public final static String findAll = base + "findAll";
	public final static String findByCode = base + "findByCode";
	public final static String findBySimbol = base + "findBySimbol";
	
	private String code;
	private String denomination;
	private String simbol;
	private Set<DenominationEntity> denominations = new HashSet<DenominationEntity>();

	private Timestamp version;

	public CurrencyEntity() {
		// TODO Auto-generated constructor stub
	}

	@NotNull
	@Size(min = 1, max = 3)
	@NotBlank
	@NotEmpty
	@Id
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@NotNull
	@Size(min = 1, max = 100)
	@NotBlank
	@NotEmpty
	public String getDenomination() {
		return denomination;
	}

	public void setDenomination(String denomination) {
		this.denomination = denomination;
	}

	@Size(min = 0, max = 5)
	@Column(nullable = true)
	public String getSimbol() {
		return simbol;
	}

	public void setSimbol(String simbol) {
		this.simbol = simbol;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "currency")
	public Set<DenominationEntity> getDenominations() {
		return denominations;
	}

	public void setDenominations(Set<DenominationEntity> denominations) {
		this.denominations = denominations;
	}

	@XmlTransient
	@Version
	public Timestamp getVersion() {
		return this.version;
	}

	public void setVersion(Timestamp version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CurrencyEntity))
			return false;
		CurrencyEntity other = (CurrencyEntity) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}

}
