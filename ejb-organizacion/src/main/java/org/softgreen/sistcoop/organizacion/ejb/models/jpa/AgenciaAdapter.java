package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.AgenciaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.BovedaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.CajaEntity;
import org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities.TrabajadorEntity;

public class AgenciaAdapter implements AgenciaModel {

	private static final long serialVersionUID = 1L;

	protected AgenciaEntity agenciaEntity;
	protected EntityManager em;

	public AgenciaAdapter(EntityManager em, AgenciaEntity agenciaEntity) {
		this.em = em;
		this.agenciaEntity = agenciaEntity;
	}

	public AgenciaEntity getAgenciaEntity() {
		return agenciaEntity;
	}

	@Override
	public void commit() {
		em.merge(agenciaEntity);
	}

	@Override
	public Integer getId() {
		return agenciaEntity.getId();
	}

	@Override
	public String getCodigo() {
		return agenciaEntity.getCodigo();
	}

	@Override
	public String getDenominacion() {
		return agenciaEntity.getDenominacion();
	}

	@Override
	public void setDenominacion(String denominacion) {
		agenciaEntity.setDenominacion(denominacion);
	}

	@Override
	public String getAbreviatura() {
		return agenciaEntity.getAbreviatura();
	}

	@Override
	public void setAbreviatura(String abreviatura) {
		agenciaEntity.setAbreviatura(abreviatura);
	}

	@Override
	public String getUbigeo() {
		return agenciaEntity.getUbigeo();
	}

	@Override
	public void setUbigeo(String ubigeo) {
		agenciaEntity.setUbigeo(ubigeo);
	}

	@Override
	public boolean getEstado() {
		return agenciaEntity.isEstado();
	}

	@Override
	public void desactivar() {
		agenciaEntity.setEstado(false);
	}

	@Override
	public SucursalModel getSucursal() {
		return new SucursalAdapter(em, agenciaEntity.getSucursal());
	}

	@Override
	public List<BovedaModel> getBovedas() {
		return getBovedas(true);
	}

	@Override
	public List<BovedaModel> getBovedas(boolean estado) {
		Set<BovedaEntity> list = agenciaEntity.getBovedas();
		List<BovedaModel> result = new ArrayList<BovedaModel>();
		for (BovedaEntity entity : list) {
			if (entity.isEstado() == estado)
				result.add(new BovedaAdapter(em, entity));
		}
		return result;
	}

	@Override
	public List<BovedaModel> getBovedas(String filterText, int firstResult, int maxResults) {
		TypedQuery<BovedaEntity> query = em.createNamedQuery(BovedaEntity.findByAgenciaAndFilterText, BovedaEntity.class);
		if (filterText == null)
			filterText = "";
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		query.setParameter("idAgencia", agenciaEntity.getId());
		query.setParameter("filterText", "%" + filterText.toUpperCase() + "%");
		List<BovedaEntity> list = query.getResultList();
		List<BovedaModel> results = new ArrayList<BovedaModel>();
		for (BovedaEntity entity : list) {
			results.add(new BovedaAdapter(em, entity));
		}
		return results;
	}

	@Override
	public List<CajaModel> getCajas() {
		return getCajas(true);
	}

	@Override
	public List<CajaModel> getCajas(boolean estado) {
		Set<CajaEntity> list = agenciaEntity.getCajas();
		List<CajaModel> result = new ArrayList<CajaModel>();
		for (CajaEntity entity : list) {
			if (entity.isEstado() == estado)
				result.add(new CajaAdapter(em, entity));
		}
		return result;
	}

	@Override
	public List<CajaModel> getCajas(String filterText, int firstResult, int maxResults) {
		TypedQuery<CajaEntity> query = em.createNamedQuery(CajaEntity.findByAgenciaAndFilterText, CajaEntity.class);
		if (filterText == null)
			filterText = "";
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		query.setParameter("idAgencia", agenciaEntity.getId());
		query.setParameter("filterText", "%" + filterText.toUpperCase() + "%");
		List<CajaEntity> list = query.getResultList();
		List<CajaModel> results = new ArrayList<CajaModel>();
		for (CajaEntity entity : list) {
			results.add(new CajaAdapter(em, entity));
		}
		return results;
	}

	@Override
	public List<TrabajadorModel> getTrabajadores() {
		return getTrabajadores(true);
	}

	@Override
	public List<TrabajadorModel> getTrabajadores(boolean estado) {
		Set<TrabajadorEntity> list = agenciaEntity.getTrabajadores();
		List<TrabajadorModel> result = new ArrayList<TrabajadorModel>();
		for (TrabajadorEntity entity : list) {
			if (entity.isEstado() == estado)
				result.add(new TrabajadorAdapter(em, entity));
		}
		return result;
	}

	@Override
	public List<TrabajadorModel> getTrabajadores(String filterText, int firstResult, int maxResults) {
		TypedQuery<TrabajadorEntity> query = em.createNamedQuery(TrabajadorEntity.findByAgenciaAndFilterText, TrabajadorEntity.class);
		if (filterText == null)
			filterText = "";
		if (firstResult != -1) {
			query.setFirstResult(firstResult);
		}
		if (maxResults != -1) {
			query.setMaxResults(maxResults);
		}
		query.setParameter("idAgencia", agenciaEntity.getId());
		query.setParameter("filterText", "%" + filterText + "%");
		List<TrabajadorEntity> list = query.getResultList();
		List<TrabajadorModel> results = new ArrayList<TrabajadorModel>();
		for (TrabajadorEntity entity : list) {
			results.add(new TrabajadorAdapter(em, entity));
		}
		return results;
	}

	public static AgenciaEntity toSucursalEntity(AgenciaModel model, EntityManager em) {
		if (model instanceof AgenciaAdapter) {
			return ((AgenciaAdapter) model).getAgenciaEntity();
		}
		return em.getReference(AgenciaEntity.class, model.getId());
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || !(o instanceof AgenciaModel))
			return false;

		AgenciaModel that = (AgenciaModel) o;
		return that.getId().equals(getId());
	}

	@Override
	public int hashCode() {
		return getId().hashCode();
	}

}
