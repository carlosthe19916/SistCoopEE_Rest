package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionBovedaEntidad")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionBovedaEntidadRepresentation extends TransaccionInternaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BovedaRepresentation bovedaOrigen;
	private EntidadRepresentation entidad;

	public BovedaRepresentation getBovedaOrigen() {
		return bovedaOrigen;
	}

	public void setBovedaOrigen(BovedaRepresentation bovedaOrigen) {
		this.bovedaOrigen = bovedaOrigen;
	}

	public EntidadRepresentation getEntidad() {
		return entidad;
	}

	public void setEntidad(EntidadRepresentation entidad) {
		this.entidad = entidad;
	}

}
