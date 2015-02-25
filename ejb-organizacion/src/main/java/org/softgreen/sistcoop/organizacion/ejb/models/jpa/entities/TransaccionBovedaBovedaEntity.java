package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@Table(name = "TRANSACCION_BOVEDA_BOVEDA")
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class TransaccionBovedaBovedaEntity extends TransaccionInternaEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private HistorialBovedaEntity historialBovedaOrigen;
	private HistorialBovedaEntity historialBovedaDestino;

	private String origen;

	private Set detalle = new HashSet(0);

	public TransaccionBovedaBovedaEntity() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public HistorialBovedaEntity getHistorialBovedaOrigen() {
		return historialBovedaOrigen;
	}

	public void setHistorialBovedaOrigen(HistorialBovedaEntity historialBovedaOrigen) {
		this.historialBovedaOrigen = historialBovedaOrigen;
	}

	public HistorialBovedaEntity getHistorialBovedaDestino() {
		return historialBovedaDestino;
	}

	public void setHistorialBovedaDestino(HistorialBovedaEntity historialBovedaDestino) {
		this.historialBovedaDestino = historialBovedaDestino;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public Set getDetalle() {
		return detalle;
	}

	public void setDetalle(Set detalle) {
		this.detalle = detalle;
	}

}
