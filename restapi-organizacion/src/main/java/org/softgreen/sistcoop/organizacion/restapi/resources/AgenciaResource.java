package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.ArrayList;
import java.util.List;

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
import javax.ws.rs.GET;
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
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.client.util.Roles;
import org.softgreen.sistcoop.organizacion.managers.SucursalManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;

@Path("/agencias")
@Stateless
@SecurityDomain("keycloak")
public class AgenciaResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected AgenciaProvider agenciaProvider;

	@Inject
	protected TrabajadorProvider trabajadorProvider;
	
	@Inject
	protected SucursalManager sucursalManager;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public AgenciaRepresentation getAgenciaById(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/codigo/{codigo}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public AgenciaRepresentation getAgenciaByCodigo(@PathParam("codigo") @NotNull @Pattern(regexp = "[0-9]+") @Size(min = 2, max = 2) String codigo) {
		AgenciaModel model = agenciaProvider.getAgenciaByCodigo(codigo);
		AgenciaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<BovedaRepresentation> getBovedas(@PathParam("id") @NotNull @Min(value = 1) Integer id, @QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText, @QueryParam("limit") @Min(value = 0) Integer limit, @QueryParam("offset") @Min(value = 0) Integer offset) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<BovedaModel> list;
		if (estado == null) {
			if (filterText == null) {
				filterText = "";
			}
			if (limit == null) {
				limit = -1;
			}
			if (offset == null) {
				offset = -1;
			}
			list = model.getBovedas(filterText, offset, limit);
		} else {
			list = model.getBovedas(estado);
		}

		List<BovedaRepresentation> result = new ArrayList<BovedaRepresentation>();
		for (BovedaModel bovedaModel : list) {
			result.add(ModelToRepresentation.toRepresentation(bovedaModel));
		}
		return result;
	}

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<CajaRepresentation> getCajas(@PathParam("id") @NotNull @Min(value = 1) Integer id, @QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText, @QueryParam("limit") @Min(value = 0) Integer limit, @QueryParam("offset") @Min(value = 0) Integer offset) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<CajaModel> list;
		if (estado == null) {
			if (filterText == null) {
				filterText = "";
			}
			if (limit == null) {
				limit = -1;
			}
			if (offset == null) {
				offset = -1;
			}
			list = model.getCajas(filterText, offset, limit);
		} else {
			list = model.getCajas(estado);
		}
						
		List<CajaRepresentation> result = new ArrayList<CajaRepresentation>();
		for (CajaModel cajaModel : list) {
			result.add(ModelToRepresentation.toRepresentation(cajaModel));
		}
		return result;
	}

	@GET
	@Path("/{id}/trabajadores")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<TrabajadorRepresentation> getTrabajadores(@PathParam("id") @NotNull @Min(value = 1) Integer id, @QueryParam("estado") Boolean estado, @QueryParam("filterText") String filterText, @QueryParam("limit") @Min(value = 0) Integer limit, @QueryParam("offset") @Min(value = 0) Integer offset) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null)
			throw new NotFoundException();

		List<TrabajadorModel> list;
		if (estado == null) {
			if (limit == null) {
				limit = -1;
			}
			if (offset == null) {
				offset = -1;
			}
			list = model.getTrabajadores(filterText, offset, limit);
		} else {
			list = model.getTrabajadores(estado);
		}

		List<TrabajadorRepresentation> result = new ArrayList<TrabajadorRepresentation>();
		for (TrabajadorModel trabajadorModel : list) {
			result.add(ModelToRepresentation.toRepresentation(trabajadorModel));
		}
		return result;
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL })
	public void updateAgencia(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid AgenciaRepresentation agenciaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Agencia inactiva, no se puede actualizar.");
		}

		model.setAbreviatura(agenciaRepresentation.getAbreviatura());
		model.setDenominacion(agenciaRepresentation.getDenominacion());
		model.setUbigeo(agenciaRepresentation.getUbigeo());
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.ADMIN)
	public void desactivar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Agencia inactiva, no se puede actualizar.");
		}

		sucursalManager.desactivarAgencia(model);
	}

	@POST
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public Response addBoveda(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid BovedaRepresentation bovedaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Agencia inactiva, no se puede actualizar.");
		}
		
		List<BovedaModel> bovedaModels = model.getBovedas();
		for (BovedaModel bovedaModel : bovedaModels) {
			if(bovedaModel.getMoneda().equalsIgnoreCase(bovedaRepresentation.getMoneda()))
				throw new BadRequestException("Moneda ya existente en agencia.");
		}

		BovedaModel bovedaModel = representationToModel.createBoveda(model, bovedaRepresentation, bovedaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(bovedaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(bovedaModel.getId())).build();
	}

	@POST
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public Response addCaja(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid CajaRepresentation cajaRepresentation) {
		AgenciaModel model = agenciaProvider.getAgenciaById(id);
		if (model == null) {
			throw new NotFoundException("Agencia not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Agencia inactiva, no se puede actualizar.");
		}

		CajaModel cajaModel = representationToModel.createCaja(model, cajaRepresentation, bovedaProvider, cajaProvider, bovedaCajaProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(cajaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(cajaModel.getId())).build();
	}
	
	@POST
	@Path("/{id}/trabajadores")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR })
	public Response addTrabajador(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid TrabajadorRepresentation rep) {
		AgenciaModel agenciaModel = agenciaProvider.getAgenciaById(id);
		if (agenciaModel == null) {
			throw new NotFoundException("Agencia not found.");
		}
		if (!agenciaModel.getEstado()) {
			throw new BadRequestException("Agencia inactiva, no se puede actualizar.");
		}
		
		TrabajadorModel modelValidate = trabajadorProvider.getTrabajadorByTipoNumeroDocumento(rep.getTipoDocumento(), rep.getNumeroDocumento());
		if(modelValidate != null){
			throw new BadRequestException("Trabajador ya registrado, no se puede continuaar.");
		}
			
		TrabajadorModel model = representationToModel.createTrabajador(agenciaModel, rep, trabajadorProvider);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(model.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(model.getId())).build();
	}

}