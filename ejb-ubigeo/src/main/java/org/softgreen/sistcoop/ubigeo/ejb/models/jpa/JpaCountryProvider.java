package org.softgreen.sistcoop.ubigeo.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.ubigeo.client.models.CountryModel;
import org.softgreen.sistcoop.ubigeo.client.models.CountryProvider;
import org.softgreen.sistcoop.ubigeo.ejb.models.jpa.entities.CountryEntity;

@Named
@Stateless
@Local(CountryProvider.class)
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class JpaCountryProvider implements CountryProvider {

	@PersistenceContext
	protected EntityManager em;

	@Override
	public CountryModel findByAlpha2Code(String code) {
		TypedQuery<CountryEntity> query = em.createNamedQuery(CountryEntity.findByAlpha2Code, CountryEntity.class);
		query.setParameter("code", code);
		List<CountryEntity> list = query.getResultList();
		if (list.size() > 0) {
			return new CountryAdapter(em, list.get(0));
		} else {
			return null;
		}
	}

	@Override
	public CountryModel findByAlpha3Code(String code) {
		TypedQuery<CountryEntity> query = em.createNamedQuery(CountryEntity.findByAlpha3Code, CountryEntity.class);
		query.setParameter("code", code);
		List<CountryEntity> list = query.getResultList();
		if (list.size() > 0) {
			return new CountryAdapter(em, list.get(0));
		} else {
			return null;
		}
	}

	@Override
	public CountryModel findByNumericCode(String code) {
		TypedQuery<CountryEntity> query = em.createNamedQuery(CountryEntity.findByNumericCode, CountryEntity.class);
		query.setParameter("code", code);
		List<CountryEntity> list = query.getResultList();
		if (list.size() > 0) {
			return new CountryAdapter(em, list.get(0));
		} else {
			return null;
		}
	}

	@Override
	public List<CountryModel> findAll() {
		TypedQuery<CountryEntity> query = em.createNamedQuery(CountryEntity.findAll, CountryEntity.class);
		List<CountryEntity> list = query.getResultList();
		List<CountryModel> results = new ArrayList<CountryModel>();
		for (CountryEntity entity : list) {
			results.add(new CountryAdapter(em, entity));
		}
		return results;
	}

	@Override
	public void close() {

	}

}
