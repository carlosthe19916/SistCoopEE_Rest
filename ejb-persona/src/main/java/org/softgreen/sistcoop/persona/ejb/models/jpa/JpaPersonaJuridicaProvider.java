package org.softgreen.sistcoop.persona.ejb.models.jpa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.persona.client.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaModel;
import org.softgreen.sistcoop.persona.client.models.PersonaJuridicaProvider;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaJuridicaEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.PersonaNaturalEntity;
import org.softgreen.sistcoop.persona.ejb.models.jpa.entities.TipoDocumentoEntity;

@Named
@Stateless
@Local(PersonaJuridicaProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaPersonaJuridicaProvider implements PersonaJuridicaProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public PersonaJuridicaModel addPersonaJuridica(PersonaNaturalModel representanteLegal, String codigoPais, TipoDocumentoModel tipoDocumentoModel, String numeroDocumento, String razonSocial, Date fechaConstitucion, TipoEmpresa tipoEmpresa, boolean finLucro) {
		TipoDocumentoEntity tipoDocumentoEntity = TipoDocumentoAdapter.toTipoDocumentoEntity(tipoDocumentoModel, em);
		PersonaNaturalEntity personaNaturalEntity = PersonaNaturalAdapter.toPersonaNaturalEntity(representanteLegal, em);

		PersonaJuridicaEntity personaJuridicaEntity = new PersonaJuridicaEntity();
		personaJuridicaEntity.setRepresentanteLegal(personaNaturalEntity);
		personaJuridicaEntity.setCodigoPais(codigoPais);
		personaJuridicaEntity.setTipoDocumento(tipoDocumentoEntity);
		personaJuridicaEntity.setNumeroDocumento(numeroDocumento);
		personaJuridicaEntity.setRazonSocial(razonSocial);
		personaJuridicaEntity.setFechaConstitucion(fechaConstitucion);
		personaJuridicaEntity.setTipoEmpresa(tipoEmpresa);
		personaJuridicaEntity.setFinLucro(finLucro);

		em.persist(personaJuridicaEntity);
		return new PersonaJuridicaAdapter(em, personaJuridicaEntity);
	}

	@Override
	public boolean removePersonaJuridica(PersonaJuridicaModel personaJuridicaModel) {
		PersonaJuridicaEntity personaJuridicaEntity = PersonaJuridicaAdapter.toPersonaJuridicaEntity(personaJuridicaModel, em);
		if (em.contains(personaJuridicaEntity))
			em.remove(personaJuridicaEntity);
		else
			em.remove(em.getReference(PersonaJuridicaEntity.class, personaJuridicaEntity.getId()));
		return true;
	}

	@Override
	public PersonaJuridicaModel getPersonaJuridicaById(Long id) {
		if(id == null)
			return null;
		PersonaJuridicaEntity personaJuridicaEntity = em.find(PersonaJuridicaEntity.class, id);
		if(personaJuridicaEntity == null)
			return null;
		return new PersonaJuridicaAdapter(em, personaJuridicaEntity);
	}

	@Override
	public PersonaJuridicaModel getPersonaJuridicaByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findByTipoAndNumeroDocumento, PersonaJuridicaEntity.class);
		query.setParameter("tipoDocumento", tipoDocumento.getAbreviatura());
		query.setParameter("numeroDocumento", numeroDocumento);
		List<PersonaJuridicaEntity> results = query.getResultList();
		if (results.size() == 0)
			return null;
		return new PersonaJuridicaAdapter(em, results.get(0));
	}

	@Override
	public List<PersonaJuridicaModel> getPersonasJuridicas() {
		return getPersonasJuridicas(-1, -1);
	}

	@Override
	public int getPersonasJuridicasCount() {
		Object count = em.createNamedQuery(PersonaJuridicaEntity.count).getSingleResult();
		return ((Number) count).intValue();
	}

	@Override
	public List<PersonaJuridicaModel> getPersonasJuridicas(int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findAll, PersonaJuridicaEntity.class);
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	@Override
	public List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento) {
		return searchForNumeroDocumento(numeroDocumento, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento, int firstResult, int maxResults) {
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findByNumeroDocumento, PersonaJuridicaEntity.class);
		query.setParameter("numeroDocumento", "%" + numeroDocumento + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	@Override
	public List<PersonaJuridicaModel> searchForFilterText(String filterText) {
		return searchForFilterText(filterText, -1, -1);
	}

	@Override
	public List<PersonaJuridicaModel> searchForFilterText(String filterText, int firstResult, int maxResults) {		
		TypedQuery<PersonaJuridicaEntity> query = em.createNamedQuery(PersonaJuridicaEntity.findByFilterText, PersonaJuridicaEntity.class);
		query.setParameter("filtertext", "%" + filterText.toUpperCase() + "%");
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		List<PersonaJuridicaEntity> results = query.getResultList();
		List<PersonaJuridicaModel> users = new ArrayList<PersonaJuridicaModel>();
		for (PersonaJuridicaEntity entity : results)
			users.add(new PersonaJuridicaAdapter(em, entity));
		return users;
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

}
