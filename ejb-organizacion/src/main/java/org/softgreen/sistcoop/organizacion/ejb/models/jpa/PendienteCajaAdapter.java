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
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionBovedaEntidadEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleTransaccionInternaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.PendienteCajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaBovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TransaccionBovedaEntidadEntity;

public class PendienteCajaAdapter implements PendienteCajaModel {

	private static final long serialVersionUID = 1L;

	protected PendienteCajaEntity pendienteCajaEntity;
	protected EntityManager em;

	public PendienteCajaAdapter(EntityManager em, PendienteCajaEntity pendienteCajaEntity) {
		this.em = em;
		this.pendienteCajaEntity = pendienteCajaEntity;
	}

	public PendienteCajaEntity getHistorialBovedaEntity() {
		return pendienteCajaEntity;
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
	public String getMoneda() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getMonto() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getObservacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTrabajador() {
		// TODO Auto-generated method stub
		return null;
	}

}
