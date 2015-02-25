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
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionInternaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaEntidadEntity;

public class TransaccionBovedaBovedaAdapter implements TransaccionBovedaBovedaModel {

	private static final long serialVersionUID = 1L;

	protected TransaccionBovedaBovedaEntity transaccionBovedaBovedaEntity;
	protected EntityManager em;

	public TransaccionBovedaBovedaAdapter(EntityManager em, TransaccionBovedaBovedaEntity transaccionBovedaBovedaEntity) {
		this.em = em;
		this.transaccionBovedaBovedaEntity = transaccionBovedaBovedaEntity;
	}

	public TransaccionBovedaBovedaEntity getHistorialBovedaEntity() {
		return transaccionBovedaBovedaEntity;
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
	public BigDecimal getSaldoDisponibleOrigen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getSaldoDisponibleDestino() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSaldoDisponibleDestino(BigDecimal saldoDisponibleDestino) {
		// TODO Auto-generated method stub

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
	public BovedaModel getOrigen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BovedaModel getDestino() {
		// TODO Auto-generated method stub
		return null;
	}

}
