package org.softgreen.sistcoop.persona.client.models;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.persona.clien.enums.TipoEmpresa;
import org.softgreen.sistcoop.persona.client.providers.Provider;

@Local
public interface PersonaJuridicaProvider extends Provider{
	
	PersonaJuridicaModel addPersonaJuridica(
			PersonaNaturalModel representanteLegal, 
			String codigoPais,
			TipoDocumentoModel tipoDocumentoModel,
			String numeroDocumento, String razonSocial,
			Date fechaConstitucion,
			TipoEmpresa tipoEmpresa,
			boolean finLucro);
	
	boolean removePersonaJuridica(PersonaJuridicaModel personaJuridicaModel);

	PersonaJuridicaModel getPersonaJuridicaById(Long id);

	PersonaJuridicaModel getPersonaJuridicaByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento);

	List<PersonaJuridicaModel> getPersonasJuridicas();

	int getPersonasJuridicasCount();

	List<PersonaJuridicaModel> getPersonasJuridicas(int firstResult,int maxResults);

	List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento);

	List<PersonaJuridicaModel> searchForNumeroDocumento(String numeroDocumento,int firstResult, int maxResults);

	List<PersonaJuridicaModel> searchForFilterText(String filterText);

	List<PersonaJuridicaModel> searchForFilterText(String filterText,int firstResult, int maxResults);
}
