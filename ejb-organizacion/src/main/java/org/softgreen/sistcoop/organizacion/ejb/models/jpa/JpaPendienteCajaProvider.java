package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

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

	

}
