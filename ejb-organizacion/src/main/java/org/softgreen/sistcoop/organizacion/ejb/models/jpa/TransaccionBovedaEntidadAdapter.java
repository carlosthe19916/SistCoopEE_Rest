package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleTransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.EntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionInternaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaEntidadEntity;

public class TransaccionBovedaEntidadAdapter implements TransaccionBovedaEntidadModel {

	private static final long serialVersionUID = 1L;

	protected TransaccionBovedaEntidadEntity transaccionBovedaEntidadEntity;
	protected EntityManager em;

	public TransaccionBovedaEntidadAdapter(EntityManager em, TransaccionBovedaEntidadEntity transaccionBovedaEntidadEntity) {
		this.em = em;
		this.transaccionBovedaEntidadEntity = transaccionBovedaEntidadEntity;
	}

	public TransaccionBovedaEntidadEntity getHistorialBovedaEntity() {
		return transaccionBovedaEntidadEntity;
	}

	@Override
	public Date getFecha() {
		return transaccionBovedaEntidadEntity.getFecha();
	}

	@Override
	public Date getHora() {
		return transaccionBovedaEntidadEntity.getHora();
	}

	@Override
	public boolean getEstado() {
		return transaccionBovedaEntidadEntity.isEstado();
	}

	@Override
	public boolean getEstadoSolicitud() {
		return transaccionBovedaEntidadEntity.isEstadoSolicitud();
	}

	@Override
	public boolean getEstadoConfirmacion() {
		return transaccionBovedaEntidadEntity.isEstadoConfirmacion();
	}

	@Override
	public void setEstadoConfirmacion() {
		transaccionBovedaEntidadEntity.setEstadoConfirmacion(true);
		;
	}

	@Override
	public List<DetalleTransaccionInternaModel> getDetalle() {

		return null;
	}

	@Override
	public void commit() {
		em.merge(transaccionBovedaEntidadEntity);
	}

	@Override
	public Long getId() {
		return transaccionBovedaEntidadEntity.getId();
	}

	@Override
	public BigDecimal getSaldoDisponible() {
		return transaccionBovedaEntidadEntity.getSaldoDisponible();
	}

	@Override
	public String getOrigen() {
		return null;
	}

	@Override
	public String getObservacion() {
		return transaccionBovedaEntidadEntity.getObservacion();
	}

	@Override
	public void setObservacion(String observacion) {
		transaccionBovedaEntidadEntity.setObservacion(observacion);
	}

	@Override
	public HistorialBovedaModel getHistorialBoveda() {
		return null;
	}

	@Override
	public EntidadModel getEntidad() {
		return null;
	}

}
