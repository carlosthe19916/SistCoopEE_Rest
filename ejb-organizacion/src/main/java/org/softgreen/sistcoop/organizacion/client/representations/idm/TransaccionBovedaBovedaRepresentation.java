package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionBovedaBoveda")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionBovedaBovedaRepresentation extends TransaccionInternaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BovedaRepresentation bovedaOrigen;
	private BovedaRepresentation bovedaDestino;

	public BovedaRepresentation getBovedaOrigen() {
		return bovedaOrigen;
	}

	public void setBovedaOrigen(BovedaRepresentation bovedaOrigen) {
		this.bovedaOrigen = bovedaOrigen;
	}

	public BovedaRepresentation getBovedaDestino() {
		return bovedaDestino;
	}

	public void setBovedaDestino(BovedaRepresentation bovedaDestino) {
		this.bovedaDestino = bovedaDestino;
	}

}
