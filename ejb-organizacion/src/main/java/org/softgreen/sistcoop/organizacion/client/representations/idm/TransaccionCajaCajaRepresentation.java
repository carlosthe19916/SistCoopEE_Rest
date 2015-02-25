package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionCajaCaja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionCajaCajaRepresentation extends TransaccionInternaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private CajaRepresentation cajaOrigen;
	private CajaRepresentation cajaDestino;

	public CajaRepresentation getCajaOrigen() {
		return cajaOrigen;
	}

	public void setCajaOrigen(CajaRepresentation cajaOrigen) {
		this.cajaOrigen = cajaOrigen;
	}

	public CajaRepresentation getCajaDestino() {
		return cajaDestino;
	}

	public void setCajaDestino(CajaRepresentation cajaDestino) {
		this.cajaDestino = cajaDestino;
	}

}
