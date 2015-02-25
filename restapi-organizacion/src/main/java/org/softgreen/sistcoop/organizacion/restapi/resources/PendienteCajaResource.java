package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.PendienteCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;

@Path("/pendienteCaja")
@Stateless
@SecurityDomain("keycloak")
public class PendienteCajaResource {

	@Inject
	protected PendienteCajaProvider pendienteCajaProvider;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public PendienteCajaRepresentation findById(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		PendienteCajaModel model = pendienteCajaProvider.getPendienteCajaById(id);
		PendienteCajaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<SucursalRepresentation> findAll(@QueryParam("idCaja") Long idCaja) {
		return null;
	}

	@GET
	@Path("/{id}/caja")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public CajaRepresentation getCaja(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		PendienteCajaModel model = pendienteCajaProvider.getPendienteCajaById(id);
		if (model == null)
			return null;
		HistorialBovedaCajaModel historialBovedaCajaModel = model.getHistorialBovedaCaja();
		BovedaCajaModel bovedaCajaModel = historialBovedaCajaModel.getBovedaCaja();
		CajaModel cajaModel = bovedaCajaModel.getCaja();
		CajaRepresentation rep = ModelToRepresentation.toRepresentation(cajaModel);

		return rep;
	}

}