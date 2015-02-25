package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionBovedaCaja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionBovedaCajaRepresentation extends TransaccionInternaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BovedaRepresentation boveda;
	private CajaRepresentation caja;

	public BovedaRepresentation getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaRepresentation boveda) {
		this.boveda = boveda;
	}

	public CajaRepresentation getCaja() {
		return caja;
	}

	public void setCaja(CajaRepresentation caja) {
		this.caja = caja;
	}

}
