package org.softgreen.sistcoop.organizacion.ejb.models.jpa.entities;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class AgenciaEntityTest {

	@Deployment
	public static Archive<?> createDeployment() {
		return ShrinkWrap.create(WebArchive.class, "test.war")
				.addPackage(AgenciaEntity.class.getPackage())
				.addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "META-INF/beans.xml");
	}

	@PersistenceContext
	EntityManager em;

	@Inject
	UserTransaction utx;

	@Test
	public void create() throws Exception {		
		utx.begin();
		em.joinTransaction();

		AgenciaEntity entity = new AgenciaEntity();

		entity.setAbreviatura("Ayac.");
		entity.setDenominacion("Agencia Ayacucho");
		entity.setEstado(true);
		entity.setUbigeo("050101");

		em.persist(entity);

		utx.commit();
	}
}
