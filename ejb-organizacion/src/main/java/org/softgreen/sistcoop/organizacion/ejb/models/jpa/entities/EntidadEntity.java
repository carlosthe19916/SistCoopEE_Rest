package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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

import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="ENTIDAD", indexes = { @Index(columnList = "id") })
@XmlRootElement
@XmlAccessorType(XmlAccessType.PROPERTY)
public class EntidadEntity {

	private Integer id;
	private String denominacion;
	private String abreviatura;
	private boolean estado;
	private Set<TransaccionBovedaEntidadEntity> transaccionesBovedaEntidad = new HashSet<TransaccionBovedaEntidadEntity>(0);

	private Timestamp optlk;

	public EntidadEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@NotNull
	@Size(min = 1, max = 60)
	@NotBlank
	@NotEmpty
	@NaturalId
	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}

	@NotNull
	@Size(min = 1, max = 20)
	@NotBlank
	@NotEmpty
	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "entidad")
	public Set<TransaccionBovedaEntidadEntity> getTransaccionesBovedaEntidad() {
		return transaccionesBovedaEntidad;
	}

	public void setTransaccionesBovedaEntidad(Set<TransaccionBovedaEntidadEntity> transaccionesBovedaEntidad) {
		this.transaccionesBovedaEntidad = transaccionesBovedaEntidad;
	}

	@XmlTransient
	@Version
	public Timestamp getOptlk() {
		return optlk;
	}

	public void setOptlk(Timestamp optlk) {
		this.optlk = optlk;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((denominacion == null) ? 0 : denominacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof EntidadEntity))
			return false;
		EntidadEntity other = (EntidadEntity) obj;
		if (denominacion == null) {
			if (other.denominacion != null)
				return false;
		} else if (!denominacion.equals(other.denominacion))
			return false;
		return true;
	}

}
