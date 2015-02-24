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
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionInternaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaEntidadEntity;

public class TransaccionBovedaCajaAdapter implements TransaccionBovedaCajaModel {

	private static final long serialVersionUID = 1L;

	protected TransaccionBovedaCajaEntity transaccionBovedaCajaEntity;
	protected EntityManager em;

	public TransaccionBovedaCajaAdapter(EntityManager em, TransaccionBovedaCajaEntity transaccionBovedaCajaEntity) {
		this.em = em;
		this.transaccionBovedaCajaEntity = transaccionBovedaCajaEntity;
	}

	public TransaccionBovedaCajaEntity getHistorialBovedaEntity() {
		return transaccionBovedaCajaEntity;
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
	public void setEstadoConfirmacion() {
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
	public HistorialBovedaModel getHistorialBoveda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistorialBovedaCajaModel getHistorialBovedaCaja() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
