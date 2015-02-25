package org.softgreen.sistcoop.organizacion.client.representations.idm;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "transaccionInterna")
@XmlAccessorType(XmlAccessType.PROPERTY)
public class TransaccionInternaRepresentation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;
	private Date fecha;
	private Date hora;
	private boolean estado;
	private String observacion;

	private boolean estadoSolicitud;
	private boolean estadoConfirmacion;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getHora() {
		return hora;
	}

	public void setHora(Date hora) {
		this.hora = hora;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}

	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public boolean isEstadoSolicitud() {
		return estadoSolicitud;
	}

	public void setEstadoSolicitud(boolean estadoSolicitud) {
		this.estadoSolicitud = estadoSolicitud;
	}

	public boolean isEstadoConfirmacion() {
		return estadoConfirmacion;
	}

	public void setEstadoConfirmacion(boolean estadoConfirmacion) {
		this.estadoConfirmacion = estadoConfirmacion;
	}

}
