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
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaProvider;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.client.util.Roles;
import org.softgreen.sistcoop.organizacion.managers.BovedaManager;

@Path("/bovedas")
@Stateless
@SecurityDomain("keycloak")
public class BovedaResource {

	@Inject
	protected BovedaProvider bovedaProvider;

	@Inject
	protected BovedaManager bovedaManager;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public BovedaRepresentation getBovedaById(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		BovedaRepresentation rep = ModelToRepresentation.toRepresentation(model);
		return rep;
	}

	@GET
	@Path("/{id}/cajas")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<CajaRepresentation> getCajasAsignadas(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		List<BovedaCajaModel> bovedaCajaList = model.getBovedaCajas();
		List<CajaRepresentation> list = new ArrayList<CajaRepresentation>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaList) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			CajaRepresentation cajaRepresentation = new CajaRepresentation();
			cajaRepresentation.setId(cajaModel.getId());
			cajaRepresentation.setDenominacion(cajaModel.getDenominacion());
			cajaRepresentation.setAbierto(cajaModel.isAbierto());
			cajaRepresentation.setEstadoMovimiento(cajaModel.getEstadoMovimiento());
			cajaRepresentation.setEstado(cajaModel.getEstado());

			list.add(cajaRepresentation);
		}
		return list;
	}

	@GET
	@Path("/{id}/detalle")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public List<DetalleHistorialRepresentation> getDetalle(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		HistorialModel historialModel = model.getHistorialActivo();
		if (historialModel != null) {
			List<DetalleHistorialModel> detalleHistorialModel = historialModel.getDetalle();
			List<DetalleHistorialRepresentation> result = new ArrayList<DetalleHistorialRepresentation>();
			for (DetalleHistorialModel detHistModel : detalleHistorialModel) {
				DetalleHistorialRepresentation detalleHistorialRepresentation = ModelToRepresentation.toRepresentation(detHistModel);
				result.add(detalleHistorialRepresentation);
			}
			return result;
		} else {
			return null;
		}
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void update(@PathParam("id") @NotNull @Min(value = 1) Integer id, @Valid BovedaRepresentation rep) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede actualizar.");
		}
		
		model.setDenominacion(rep.getDenominacion());
		model.commit();
	}

	@POST
	@Path("/{id}/desactivar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.ADMIN)
	public void desactivar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (model.isAbierto())
			throw new BadRequestException("Boveda abierta, no se puede desactivar");
		
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede desactivar nuevamente.");
		}
		
		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			BigDecimal saldo = bovCajModel.getSaldo();
			CajaModel caja = bovCajModel.getCaja();
			if (caja.isAbierto()) {
				throw new EJBException("Caja abierta no se puede desactivar boveda.");
			}
			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				throw new EJBException("La boveda tiene saldo asignado a caja diferente de cero.");
			}									
		}
		
		bovedaManager.desactivarBoveda(model);
	}

	@POST
	@Path("/{id}/abrir")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.JEFE_CAJA)
	public void abrir(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (model.isAbierto()) {
			throw new BadRequestException("Boveda abierta, no se puede abrir nuevamente.");
		}		
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede abrir.");
		}
		
		bovedaManager.abrir(model);
	}

	@POST
	@Path("/{id}/cerrar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed(Roles.JEFE_CAJA)
	public void cerrar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (!model.isAbierto()) {
			throw new BadRequestException("Boveda cerrada, no se puede cerrar nuevamente.");
		}		
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede cerrar.");
		}
		
		bovedaManager.cerrar(model);
	}

	@POST
	@Path("/{id}/congelar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void congelar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}		
		if (!model.isAbierto()) {
			throw new BadRequestException("Boveda cerrada, no se puede congelar.");
		}		
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede congelar.");
		}	
		
		bovedaManager.congelar(model);
	}

	@POST
	@Path("/{id}/descongelar")
	@Produces({ "application/xml", "application/json" })
	@RolesAllowed({ Roles.ADMIN, Roles.GERENTE_GENERAL, Roles.ADMINISTRADOR_GENERAL, Roles.ADMINISTRADOR, Roles.JEFE_CAJA })
	public void descongelar(@PathParam("id") @NotNull @Min(value = 1) Integer id) {
		BovedaModel model = bovedaProvider.getBovedaById(id);
		if (model == null) {
			throw new NotFoundException("Boveda not found.");
		}
		if (!model.isAbierto()) {
			throw new BadRequestException("Boveda cerrada, no se puede congelar.");
		}		
		if (!model.getEstado()) {
			throw new BadRequestException("Boveda inactiva, no se puede congelar.");
		}
		bovedaManager.descongelar(model);
	}

}