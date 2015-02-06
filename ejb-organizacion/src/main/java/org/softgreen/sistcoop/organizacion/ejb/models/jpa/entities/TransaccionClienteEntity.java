package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="TRANSACCION_CLIENTE")
@Inheritance(strategy = InheritanceType.JOINED)
public class TransaccionClienteEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Long numeroOperacion;
	private Date fecha;
	private Date hora;
	private boolean estado;
	private String observacion;

	private HistorialBovedaCajaEntity historialBovedaCaja;
	private Set<DetalleTransaccionClienteEntity> detalle = new HashSet<DetalleTransaccionClienteEntity>();

	private Timestamp optlk;

	public TransaccionClienteEntity() {
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(generator = "SgGenericGenerator")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getNumeroOperacion() {
		return numeroOperacion;
	}

	public void setNumeroOperacion(Long numeroOperacion) {
		this.numeroOperacion = numeroOperacion;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	@NotNull
	@Type(type = "org.hibernate.type.TrueFalseType")
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@NotNull
	@Size(min = 0, max = 60)
	@NotBlank
	@NotEmpty
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(foreignKey = @ForeignKey)
	public HistorialBovedaCajaEntity getHistorialBovedaCaja() {
		return historialBovedaCaja;
	}

	public void setHistorialBovedaCaja(HistorialBovedaCajaEntity historialBovedaCaja) {
		this.historialBovedaCaja = historialBovedaCaja;
	}

	@XmlTransient
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "transaccionCliente")
	public Set<DetalleTransaccionClienteEntity> getDetalle() {
		return detalle;
	}

	public void setDetalle(Set<DetalleTransaccionClienteEntity> detalle) {
		this.detalle = detalle;
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
		result = prime * result + ((fecha == null) ? 0 : fecha.hashCode());
		result = prime * result + ((numeroOperacion == null) ? 0 : numeroOperacion.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof TransaccionClienteEntity))
			return false;
		TransaccionClienteEntity other = (TransaccionClienteEntity) obj;
		if (fecha == null) {
			if (other.fecha != null)
				return false;
		} else if (!fecha.equals(other.fecha))
			return false;
		if (numeroOperacion == null) {
			if (other.numeroOperacion != null)
				return false;
		} else if (!numeroOperacion.equals(other.numeroOperacion))
			return false;
		return true;
	}

}
