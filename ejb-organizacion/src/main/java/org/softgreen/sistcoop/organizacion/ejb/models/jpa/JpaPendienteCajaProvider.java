package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaProvider;

@Named
@Stateless
@Local(PendienteCajaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaPendienteCajaProvider implements PendienteCajaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub
	}

	@Override
	public PendienteCajaModel addPendiente(HistorialBovedaCajaModel historialBovedaCajaModel, Date fecha, Date hora, BigDecimal monto, String trabajador) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PendienteCajaModel getPendienteCajaById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PendienteCajaModel> getPendientesCaja(HistorialBovedaCajaModel historialBovedaCajaModel) {
		// TODO Auto-generated method stub
		return null;
	}

}
