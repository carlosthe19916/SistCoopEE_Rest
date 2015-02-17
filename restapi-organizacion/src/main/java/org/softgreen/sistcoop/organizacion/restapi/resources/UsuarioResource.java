package org.softgreen.sistcoop.organizacion.restapi.resources;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import org.jboss.ejb3.annotation.SecurityDomain;
import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorProvider;
import org.softgreen.sistcoop.organizacion.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;

@Path("/usuarios")
@Stateless
@SecurityDomain("keycloak")
public class UsuarioResource {

	@Inject
	protected TrabajadorProvider trabajadorProvider;

	@GET
	@Path("/{username}/sucursal")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public SucursalRepresentation getSucursal(@PathParam("username") String username) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByUsuario(username);
		if (trabajadorModel == null)
			return null;

		AgenciaModel agenciaModel = trabajadorModel.getAgencia();
		SucursalModel sucursalModel = agenciaModel.getSucursal();
		return ModelToRepresentation.toRepresentation(sucursalModel);
	}

	@GET
	@Path("/{username}/agencia")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public AgenciaRepresentation getAgencia(@PathParam("username") String username) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByUsuario(username);
		if (trabajadorModel == null)
			return null;

		AgenciaModel agenciaModel = trabajadorModel.getAgencia();
		return ModelToRepresentation.toRepresentation(agenciaModel);
	}

	@GET
	@Path("/{username}/trabajador")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public TrabajadorRepresentation getTrabajador(@PathParam("username") String username) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByUsuario(username);
		return ModelToRepresentation.toRepresentation(trabajadorModel);
	}

	@GET
	@Path("/{username}/caja")
	@Produces({ "application/xml", "application/json" })
	@PermitAll
	public CajaRepresentation getCaja(@PathParam("username") String username) {
		TrabajadorModel trabajadorModel = trabajadorProvider.getTrabajadorByUsuario(username);
		if (trabajadorModel == null)
			return null;

		List<TrabajadorCajaModel> trabajadorCajas = trabajadorModel.getTrabajadorCajas();
		TrabajadorCajaModel trabajadorCajaModel = null;
		for (TrabajadorCajaModel trabCajModel : trabajadorCajas) {
			trabajadorCajaModel = trabCajModel;
			break;
		}
		if (trabajadorCajaModel != null) {
			CajaModel cajaModel = trabajadorCajaModel.getCaja();
			return ModelToRepresentation.toRepresentation(cajaModel);
		} else {
			return null;
		}
	}

}