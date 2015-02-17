package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "trabajador")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TrabajadorRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String tipoDocumento;
	private String numeroDocumento;
	private String usuario;
	private boolean estado;

	private CajaRepresentation caja;
	private AgenciaRepresentation agencia;

	@XmlAttribute
	@Min(value = 1)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlAttribute
	@Size(min = 1, max = 20)
	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	@XmlAttribute
	@Size(min = 1, max = 20)
	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	@XmlAttribute
	@Size(min = 1, max = 40)
	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	@XmlAttribute
	@AssertTrue
	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	@XmlElement
	public CajaRepresentation getCaja() {
		return caja;
	}

	public void setCaja(CajaRepresentation caja) {
		this.caja = caja;
	}

	@XmlElement
	public AgenciaRepresentation getAgencia() {
		return agencia;
	}

	public void setAgencia(AgenciaRepresentation agencia) {
		this.agencia = agencia;
	}

}
