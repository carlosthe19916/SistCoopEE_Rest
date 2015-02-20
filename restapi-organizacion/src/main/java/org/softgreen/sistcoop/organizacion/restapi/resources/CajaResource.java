package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.ws.rs.BadRequestException;
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
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.client.util.Roles;
import org.softgreen.sistcoop.organizacion.managers.CajaManager;
import org.softgreen.sistcoop.organizacion.restapi.config.Jsend;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialCajaRepresentation;

@Path("/cajas")
@Stateless
@SecurityDomain("keycloak")
public class CajaResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected CajaProvider cajaProvider;

	@Inject
	protected CajaManager cajaManager;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public CajaRepresentation getCajaById(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		CajaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<BovedaRepresentation> getBovedasAsignadas(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<BovedaCajaModel> bovedaCajaList = model.getBovedaCajas();
		List<BovedaRepresentation> result = new ArrayList<BovedaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			BovedaRepresentation bovedaRepresentation = new BovedaRepresentation();
			bovedaRepresentation.setId(bovedaModel.getId());
			bovedaRepresentation.setMoneda(bovedaModel.getMoneda());
			bovedaRepresentation.setDenominacion(bovedaModel.getDenominacion());
			bovedaRepresentation.setAbierto(bovedaModel.isAbierto());
			bovedaRepresentation.setEstadoMovimiento(bovedaModel.getEstadoMovimiento());
			bovedaRepresentation.setEstado(bovedaModel.getEstado());

			result.add(bovedaRepresentation);
		}
		return result;
	}

	@GET
	@Path("/{id}/trabajadores")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<TrabajadorRepresentation> getTrabajadoresAsignados(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<TrabajadorCajaModel> trabajadorCajaList = model.getTrabajadorCajas();
		List<TrabajadorRepresentation> result = new ArrayList<TrabajadorRepresentation>();
		for (TrabajadorCajaModel trabajadorCajaModel : trabajadorCajaList) {
			TrabajadorModel trabajadorModel = trabajadorCajaModel.getTrabajador();
			TrabajadorRepresentation trabajadorRepresentation = new TrabajadorRepresentation();
			trabajadorRepresentation.setId(trabajadorModel.getId());
			trabajadorRepresentation.setTipoDocumento(trabajadorModel.getTipoDocumento());
			trabajadorRepresentation.setNumeroDocumento(trabajadorModel.getNumeroDocumento());
			trabajadorRepresentation.setUsuario(trabajadorModel.getUsuario());
			trabajadorRepresentation.setEstado(trabajadorModel.getEstado());

			result.add(trabajadorRepresentation);
		}
		return result;
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<DetalleHistorialCajaRepresentation> getDetalle(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		List<BovedaCajaModel> bovedaCajaModels = model.getBovedaCajas();

		List<HistorialModel> historialesActivos = new ArrayList<HistorialModel>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
			if (historialModel != null)
				historialesActivos.add(historialModel);
		}

		boolean firstTime;
		if (historialesActivos.size() == 0) {
			firstTime = true;
		} else if (historialesActivos.size() == bovedaCajaModels.size()) {
			firstTime = false;
		} else {
			throw new EJBException("Error interno, existen cajas que no tienen historiales. Pongase en contacto con el area de sistemas.");
		}

		if (firstTime) {
			return null;
		} else {
			List<DetalleHistorialCajaRepresentation> result = new ArrayList<DetalleHistorialCajaRepresentation>();
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
				HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
				List<DetalleHistorialModel> detalleHistorialModels = historialModel.getDetalle();

				List<DetalleHistorialRepresentation> detalleHistorialRepresentations = new ArrayList<DetalleHistorialRepresentation>();
				for (DetalleHistorialModel detalleHistorialModel : detalleHistorialModels) {
					int cantidad = detalleHistorialModel.getCantidad();
					BigDecimal valor = detalleHistorialModel.getValor();

					DetalleHistorialRepresentation detalleHistorialRepresentation = new DetalleHistorialRepresentation();
					detalleHistorialRepresentation.setCantidad(cantidad);
					detalleHistorialRepresentation.setValor(valor);

					detalleHistorialRepresentations.add(detalleHistorialRepresentation);
				}

				DetalleHistorialCajaRepresentation detalleHistorialCajaRepresentation = new DetalleHistorialCajaRepresentation(detalleHistorialRepresentations);
				detalleHistorialCajaRepresentation.setBoveda(ModelToRepresentation.toRepresentation(bovedaModel));
				result.add(detalleHistorialCajaRepresentation);
			}
			return result;
		}
	}

	@GET
	@Path("/{id}/detalle/penultimo")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<DetalleHistorialCajaRepresentation> getPenultimoDetalle(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		return null;
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void update(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid CajaRepresentation rep) {
		CajaModel model = cajaProvider.getCajaById(id);
		
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede cerrar.");
		}
		
		model.setDenominacion(rep.getDenominacion());
		model.commit();
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.ADMIN)
	public void desactivar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede cerrar.");
		}
		
		cajaManager.desactivarCaja(model);
	}

	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.JEFE_CAJA)
	public void abrir(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede cerrar.");
		}
		
		cajaManager.abrir(model);
	}

	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.CAJERO)
	public void cerrar(@PathParam("id") @NotNull @Min(value = 1) Integer id, @NotNull List<DetalleHistorialCajaRepresentation> detalle) {
		CajaModel model = cajaProvider.getCajaById(id);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede cerrar.");
		}
		
		cajaManager.cerrar(model, detalle);
	}

	@POST
	@Path("/{id}/bovedas")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public Response addBoveda(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid BovedaRepresentation bovedaRepresentation) {
		CajaModel model = cajaProvider.getCajaById(id);
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(bovedaRepresentation.getId());
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (bovedaModel == null) {
			throw new NotFoundException("Boveda not found.");
		}		
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede cerrar.");
		}
		if (!bovedaModel.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede vincular.");
		}
		if (model.isAbierto()) {
			throw new InternalServerErrorException("Caja abierta, debe cerrarla antes de vincular boveda.");
		}		
		
		List<BovedaCajaModel> bovedaCajaModels = model.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel2 = bovedaCajaModel.getBoveda();
			if(bovedaCajaModel.equals(bovedaModel2)){
				throw new BadRequestException("Boveda ya fue asignada.");
			}
			if(bovedaModel2.getMoneda().equals(bovedaModel.getMoneda())){
				throw new BadRequestException("Boveda con moneda " + bovedaModel.getMoneda() + " ya fue asignada.");
			}
		}

		BovedaCajaModel bovedaCajaModel = cajaManager.addBoveda(model, bovedaModel);
		return Response.created(uriInfo.getAbsolutePathBuilder().path(bovedaCajaModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(bovedaCajaModel.getId())).build();
	}

	@POST
	@Path("/{id}/bovedas/{idBoveda}/desactivar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void desactivarBovedaCaja(@PathParam("id") @NotNull @Min(value = 1) Integer id, @PathParam("idBoveda") @NotNull @Min(value = 1) Integer idBoveda) {
		CajaModel model = cajaProvider.getCajaById(id);
		BovedaModel bovedaModel = bovedaProvider.getBovedaById(idBoveda);
		if (model == null) {
			throw new NotFoundException("Caja not found.");
		}
		if (bovedaModel == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Caja inactiva, no se puede desvincular.");
		}
		if (!bovedaModel.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede desvincular.");
		}
		if (model.isAbierto()) {
			throw new InternalServerErrorException("Caja abierta, debe cerrarla antes de desvincular boveda.");
		}
		/*if (bovedaModel.isAbierto()) {
			throw new InternalServerErrorException("Boveda abierta, debe cerrarla antes de desvincular boveda.");
		}*/

		BovedaCajaModel bovedaCajaModelToRemove = null;
		List<BovedaCajaModel> bovedaCajaModels = model.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel2 = bovedaCajaModel.getBoveda();
			if (bovedaModel.equals(bovedaModel2)) {
				bovedaCajaModelToRemove = bovedaCajaModel;
			}
		}

		if (bovedaCajaModelToRemove == null) {
			throw new NotFoundException("BovedaCaja not found.");
		}

		BigDecimal saldo = bovedaCajaModelToRemove.getSaldo();
		if (saldo.compareTo(BigDecimal.ZERO) != 0) {
			throw new InternalServerErrorException("Caja tiene saldo diferente de 0.");
		}

		bovedaCajaModelToRemove.setEstado(false);
		bovedaCajaModelToRemove.commit();
	}

}