package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.DetalleHistorialEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.HistorialEntity;

@Named
@Stateless
@Local(DetalleHistorialProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaDetalleHistorialProvider implements DetalleHistorialProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public DetalleHistorialModel addDetalleHistorial(HistorialModel historialModel, int cantidad, BigDecimal valor) {
		DetalleHistorialEntity detalleHistorialEntity = new DetalleHistorialEntity();
		HistorialEntity historialEntity = HistorialAdapter.toHistorialEntity(historialModel, em);

		detalleHistorialEntity.setCantidad(cantidad);
		detalleHistorialEntity.setValor(valor);
		detalleHistorialEntity.setHistorial(historialEntity);

		em.persist(detalleHistorialEntity);
		return new DetalleHistorialAdapter(em, detalleHistorialEntity);
	}

	@Override
	public DetalleHistorialModel getDetalleHistorialById(Long id) {
		DetalleHistorialEntity detalleHistorialEntity = this.em.find(DetalleHistorialEntity.class, id);
		return detalleHistorialEntity != null ? new DetalleHistorialAdapter(em, detalleHistorialEntity) : null;
	}

}
