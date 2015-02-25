package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleTransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.EntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHora() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEstado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void desactivar() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean getEstadoSolicitud() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getEstadoConfirmacion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void confirmar() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DetalleTransaccionInternaModel> getDetalle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSaldoDisponible() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOrigen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObservacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setObservacion(String observacion) {
		// TODO Auto-generated method stub

	}

	@Override
	public BovedaModel getBoveda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EntidadModel getEntidad() {
		// TODO Auto-generated method stub
		return null;
	}

}
