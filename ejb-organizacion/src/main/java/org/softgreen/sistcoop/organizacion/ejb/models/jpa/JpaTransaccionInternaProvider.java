package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.Date;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.EntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaProvider;

@Named
@Stateless
@Local(TransaccionInternaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaTransaccionInternaProvider implements TransaccionInternaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public TransaccionInternaModel addTransaccionInterna(EntidadModel entidadModel, BovedaModel bovedaModel, String observacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaccionInternaModel addTransaccionInterna(BovedaModel bovedaModelOrigen, BovedaModel bovedaModelDestino, String observacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaccionInternaModel addTransaccionInterna(BovedaModel bovedaModelOrigen, CajaModel cajaModel, String observacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaccionInternaModel addTransaccionInterna(CajaModel cajaModelOrigen, CajaModel cajaModelDestino, String observacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TransaccionInternaModel getTransaccionInternaById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
