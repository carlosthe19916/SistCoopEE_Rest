package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "bovedaCaja")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class BovedaCajaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private Boolean estado;
	private BigDecimal saldo;

	private BovedaRepresentation boveda;
	private CajaRepresentation caja;

	@XmlAttribute
	@Min(value = 1)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	@AssertTrue
	public Boolean isEstado() {
		return estado;
	}

	public void setEstado(Boolean estado) {
		this.estado = estado;
	}

	@XmlAttribute
	@Min(value = 0)
	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	@XmlElement
	public BovedaRepresentation getBoveda() {
		return boveda;
	}

	public void setBoveda(BovedaRepresentation boveda) {
		this.boveda = boveda;
	}

	@XmlElement
	public CajaRepresentation getCaja() {
		return caja;
	}

	public void setCaja(CajaRepresentation caja) {
		this.caja = caja;
	}

}
