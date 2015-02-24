package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.EntityManager;

import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.PendienteCajaEntity;

public class PendienteCajaAdapter implements PendienteCajaModel {

	private static final long serialVersionUID = 1L;

	protected PendienteCajaEntity pendienteCajaEntity;
	protected EntityManager em;

	public PendienteCajaAdapter(EntityManager em, PendienteCajaEntity pendienteCajaEntity) {
		this.em = em;
		this.pendienteCajaEntity = pendienteCajaEntity;
	}

	public PendienteCajaEntity getPendienteCajaEntity() {
		return pendienteCajaEntity;
	}

	@Override
	public void commit() {
		this.em.merge(pendienteCajaEntity);
	}

	@Override
	public Long getId() {
		return pendienteCajaEntity.getId();
	}

	@Override
	public Date getFecha() {
		return pendienteCajaEntity.getFecha();
	}

	@Override
	public Date getHora() {
		return pendienteCajaEntity.getHora();
	}

	@Override
	public BigDecimal getMonto() {
		return pendienteCajaEntity.getMonto();
	}

	@Override
	public String getObservacion() {
		return pendienteCajaEntity.getObservacion();
	}

	@Override
	public String getTrabajador() {
		return pendienteCajaEntity.getTrabajador();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof PendienteCajaModel))
			return false;

		PendienteCajaModel that = (PendienteCajaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

	@Override
	public HistorialBovedaCajaModel getHistorialBovedaCaja() {
		// TODO Auto-generated method stub
		return null;
	}

}
