package org.softgreen.sistcoop.persona.restapi.resources;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import org.softgreen.sistcoop.persona.client.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.client.enums.Sexo;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalModel;
import org.softgreen.sistcoop.persona.client.models.PersonaNaturalProvider;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoModel;
import org.softgreen.sistcoop.persona.client.models.TipoDocumentoProvider;
import org.softgreen.sistcoop.persona.client.models.util.ModelToRepresentation;
import org.softgreen.sistcoop.persona.client.models.util.RepresentationToModel;
import org.softgreen.sistcoop.persona.client.representations.idm.PersonaNaturalRepresentation;
import org.softgreen.sistcoop.persona.restapi.config.Jsend;

@Path("/personas/naturales")
@Stateless
public class PersonaNaturalResource {

	@Inject
	protected PersonaNaturalProvider personaNaturalProvider;

	@Inject
	protected TipoDocumentoProvider tipoDocumentoProvider;

	@Inject
	protected RepresentationToModel representationToModel;

	@Context
	protected UriInfo uriInfo;

	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public PersonaNaturalRepresentation findById(@PathParam("id") Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return rep;
	}

	@GET
	@Path("/buscar")
	@Produces({ "application/xml", "application/json" })
	public PersonaNaturalRepresentation findByTipoNumeroDocumento(@QueryParam("tipoDocumento") String tipoDocumento, @QueryParam("numeroDocumento") String numeroDocumento) {
		if (tipoDocumento == null)
			return null;
		if (numeroDocumento == null)
			return null;

		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(tipoDocumento);
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalByTipoNumeroDoc(tipoDocumentoModel, numeroDocumento);
		PersonaNaturalRepresentation rep = ModelToRepresentation.toRepresentation(personaNaturalModel);
		return rep;
	}

	@GET
	@Produces({ "application/xml", "application/json" })
	public List<PersonaNaturalRepresentation> listAll(@QueryParam("filterText") String filterText, @QueryParam("offset") Integer offset, @QueryParam("limit") Integer limit) {
		List<PersonaNaturalRepresentation> results = new ArrayList<PersonaNaturalRepresentation>();
		List<PersonaNaturalModel> userModels;
		if (filterText == null) {
			if (offset == null || limit == null) {
				userModels = personaNaturalProvider.getPersonasNaturales();
			} else {
				userModels = personaNaturalProvider.getPersonasNaturales(offset, limit);
			}
		} else {
			if (offset == null || limit == null) {
				userModels = personaNaturalProvider.searchForFilterText(filterText);
			} else {
				userModels = personaNaturalProvider.searchForFilterText(filterText, offset, limit);
			}
		}
		for (PersonaNaturalModel personaNaturalModel : userModels) {
			results.add(ModelToRepresentation.toRepresentation(personaNaturalModel));
		}
		return results;
	}

	@GET
	@Path("/count")
	@Produces({ "application/xml", "application/json" })
	public int countAll() {
		return personaNaturalProvider.getPersonasNaturalesCount();
	}

	@POST
	@Produces({ "application/xml", "application/json" })
	public Response create(PersonaNaturalRepresentation personaNaturalRepresentation) {
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(personaNaturalRepresentation.getTipoDocumento());
		PersonaNaturalModel personaNaturalModel = representationToModel.createPersonaNatural(personaNaturalRepresentation, tipoDocumentoModel, personaNaturalProvider);		
		return Response.created(uriInfo.getAbsolutePathBuilder().path(personaNaturalModel.getId().toString()).build()).header("Access-Control-Expose-Headers", "Location").entity(Jsend.getSuccessJSend(personaNaturalModel.getId())).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" })
	public void update(@PathParam("id") Long id, PersonaNaturalRepresentation rep) {
		PersonaNaturalModel model = personaNaturalProvider.getPersonaNaturalById(id);
		TipoDocumentoModel tipoDocumentoModel = tipoDocumentoProvider.getTipoDocumentoByAbreviatura(rep.getTipoDocumento());

		model.setCodigoPais(rep.getCodigoPais());
		model.setTipoDocumento(tipoDocumentoModel);
		model.setNumeroDocumento(rep.getNumeroDocumento());
		model.setApellidoPaterno(rep.getApellidoPaterno());
		model.setApellidoMaterno(rep.getApellidoMaterno());
		model.setNombres(rep.getNombres());
		model.setFechaNacimiento(rep.getFechaNacimiento());
		model.setSexo(Sexo.valueOf(rep.getSexo().toUpperCase()));
		model.setEstadoCivil(rep.getEstadoCivil() != null ? EstadoCivil.valueOf(rep.getEstadoCivil().toUpperCase()) : null);

		model.setUbigeo(rep.getUbigeo());
		model.setDireccion(rep.getDireccion());
		model.setReferencia(rep.getReferencia());
		model.setTelefono(rep.getTelefono());
		model.setCelular(rep.getCelular());
		model.setEmail(rep.getEmail());

		model.commit();
	}

	@DELETE
	@Path("/{id}")
	public void remove(@PathParam("id") Long id) {
		PersonaNaturalModel personaNaturalModel = personaNaturalProvider.getPersonaNaturalById(id);
		personaNaturalProvider.removePersonaNatural(personaNaturalModel);
	}

}