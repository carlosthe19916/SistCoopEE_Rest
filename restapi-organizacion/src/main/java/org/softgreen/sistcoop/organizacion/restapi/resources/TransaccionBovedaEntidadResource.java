package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.List;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.EntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.EntidadProvider;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.EntidadRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionBovedaEntidadRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionInternaRepresentation;
import org.softgreen.sistcoop.organizacion.client.util.Roles;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;

@Path("/transaccionBovedaEntidad")
@Stateless
@SecurityDomain("keycloak")
public class TransaccionBovedaEntidadResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected EntidadProvider entidadProvider;

	@Inject
	protected TransaccionInternaProvider transaccionInternaProvider;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public TransaccionBovedaEntidadRepresentation findById(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		TransaccionInternaModel model = transaccionInternaProvider.getTransaccionInternaById(id);
		TransaccionInternaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return (TransaccionBovedaEntidadRepresentation) rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<TransaccionBovedaEntidadRepresentation> findAll() {
		return null;
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL })
	public Response create(@Valid TransaccionBovedaEntidadRepresentation rep) {
		BovedaRepresentation bovedaRepresentation = rep.getBovedaOrigen();
		EntidadRepresentation entidadRepresentation = rep.getEntidad();

		BovedaModel bovedaModel = bovedaProvider.getBovedaById(bovedaRepresentation.getId());
		EntidadModel entidadModel = entidadProvider.getEntidadById(entidadRepresentation.getId());
		if (bovedaModel == null)
			throw new NotFoundException("Boveda no encontrada.");
		if (entidadModel == null)
			throw new NotFoundException("Entidad no encontrada.");

		TransaccionInternaModel transaccionInternaModel = transaccionInternaProvider.addTransaccionInterna(entidadModel, bovedaModel, rep.getObservacion());
		TransaccionBovedaEntidadModel model = (TransaccionBovedaEntidadModel) transaccionInternaModel;
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(model.getId())).build();
	}

	@PUT
	@Path("/{id}/confirmar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL })
	public void update(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		TransaccionInternaModel model = transaccionInternaProvider.getTransaccionInternaById(id);
		if (model == null) {
			throw new NotFoundException("Transaccion no encontrada.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Transaccion inactiva, no se puede actualizar.");
		}

		model.confirmar();
		model.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@DenyAll
	public void delete(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		throw new InternalServerErrorException("No se pudo eliminar el elemento");
	}

	@POST
	@Path("/{id}/cancelar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.ADMIN)
	public void desactivar(@PathParam("id") @NotNull @Min(value = 1) Long id) {
		TransaccionInternaModel model = transaccionInternaProvider.getTransaccionInternaById(id);
		if (model == null) {
			throw new NotFoundException("Transaccion no encontrada.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Transaccion inactiva, no se puede actualizar.");
		}

		model.desactivar();
		model.commit();
	}
}