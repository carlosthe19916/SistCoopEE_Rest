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
import org.softgreen.sistcoop.organizacion.client.models.TransaccionCajaCajaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionInternaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionCajaCajaEntity;

public class TransaccionCajaCajaAdapter implements TransaccionCajaCajaModel {

	private static final long serialVersionUID = 1L;

	protected TransaccionCajaCajaEntity transaccionCajaCajaEntity;
	protected EntityManager em;

	public TransaccionCajaCajaAdapter(EntityManager em, TransaccionCajaCajaEntity transaccionCajaCajaEntity) {
		this.em = em;
		this.transaccionCajaCajaEntity = transaccionCajaCajaEntity;
	}

	public TransaccionCajaCajaEntity getHistorialBovedaEntity() {
		return transaccionCajaCajaEntity;
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
	public String getObservacion() {
		// TODO Auto-generated method stub
		return null;
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
	public String getMoneda() {
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
	public BigDecimal getMonto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistorialBovedaCajaModel getHistorialOrigen() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HistorialBovedaCajaModel getHistorialDestino() {
		// TODO Auto-generated method stub
		return null;
	}

	

	

}
