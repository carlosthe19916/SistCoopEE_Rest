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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.client.util.Roles;
import org.softgreen.sistcoop.organizacion.managers.TrabajadorManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;

@Path("/trabajadores")
@Stateless
@SecurityDomain("keycloak")
public class TrabajadorResource {

	@Inject
	protected AgenciaProvider agenciaProvider;

	@Inject
	protected TrabajadorProvider trabajadorProvider;

	@Inject
	protected TrabajadorCajaProvider trabajadorCajaProvider;

	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected RepresentationToModel representationToModel;

	@Inject
	protected TrabajadorManager trabajadorManager;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public TrabajadorRepresentation findById(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		TrabajadorRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public TrabajadorRepresentation findByTipoNumeroDocumento(@QueryParam("tipoDocumento") @NotNull @Size(min = 1, max = 20) String tipoDocumento, @QueryParam("numeroDocumento") @NotNull @Pattern(regexp = "[0-9]+") @Size(min = 1, max = 20) String numeroDocumento) {
		if (tipoDocumento == null)
			return null;
		if (numeroDocumento == null)
			return null;

		TrabajadorModel model = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(tipoDocumento, numeroDocumento);
		TrabajadorRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR })
	public Response create(@Valid TrabajadorRepresentation rep) {
		AgenciaRepresentation agenciaRepresentation = rep.getAgencia();
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(agenciaRepresentation.getId());
		TrabajadorModel model = representationToModel.createTrabajador(agenciaModel, rep, trabajadorProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(model.getId())).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR })
	public void update(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid TrabajadorRepresentation rep) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		if (model == null)
			throw new NotFoundException();
		if (!model.getEstado()) {
			throw new BadRequestException("Trabajador inactio, no se puede actualizar.");
		}

		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(rep.getAgencia().getId());
		model.setUsuario(rep.getUsuario());
		model.setAgencia(agenciaModel);
		model.commit();
	}

	@DELETE
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@DenyAll
	public void delete(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		boolean removed = trabajadorProvider.removeTrabajador(model);
		if (!removed)
			throw new InternalServerErrorException("No se pudo eliminar el elemento");
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.ADMIN)
	public void desactivar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		if (model == null) {
			throw new NotFoundException("Trabajador no encontrado.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Trabajador inactivo, no se puede desactivar nuevamente.");
		}

		trabajadorManager.desactivarTrabajador(model);
	}

	@POST
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public Response setCaja(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid CajaRepresentation cajaRepresentation) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		CajaModel cajaModel = cajaProvider.getCajaById(cajaRepresentation.getId());
		if (model == null) {
			throw new NotFoundException("Trabajador not found.");
		}
		if (cajaModel == null) {
			throw new NotFoundException("Caja not found.");
		}

		if (!model.getEstado()) {
			throw new BadRequestException("Trabajador inactivo, no se puede actualizar.");
		}
		if (!cajaModel.getEstado()) {
			throw new NotFoundException("Caja inactiva, no se puede asignar.");
		}

		List<TrabajadorCajaModel> trabajadorCajaModels = model.getTrabajadorCajas();
		if (trabajadorCajaModels.size() > 0) {
			TrabajadorCajaModel trabajadorCajaModel = trabajadorCajaModels.get(0);
			CajaModel cajaModelDB = trabajadorCajaModel.getCaja();
			if (!cajaModel.equals(cajaModelDB)) {
				TrabajadorCajaModel trabajadorCajaModelNew = trabajadorManager.setCaja(model, cajaModel);
				return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorCajaModelNew.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(trabajadorCajaModelNew.getId())).build();
			} else {
				return Response.noContent().build();
			}
		} else {
			TrabajadorCajaModel trabajadorCajaModelNew = trabajadorManager.setCaja(model, cajaModel);
			return Response.created(uriInfo.getAbsolutePathBuilder().path(trabajadorCajaModelNew.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(trabajadorCajaModelNew.getId())).build();
		}

	}

	@DELETE
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void removeCaja(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		TrabajadorModel model = trabajadorProvider.getTrabajadorById(id);
		if (model == null) {
			throw new NotFoundException("Trabajador not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Trabajador inactivo, no se puede actualizar.");
		}

		List<TrabajadorCajaModel> trabajadorCajaModels = model.getTrabajadorCajas();
		if (trabajadorCajaModels.size() > 0) {
			TrabajadorCajaModel trabajadorCajaModel = trabajadorCajaModels.get(0);
			trabajadorCajaProvider.removeTrabajadorCaja(trabajadorCajaModel);
		}
	}

}