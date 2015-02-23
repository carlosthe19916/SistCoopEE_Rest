package org.softgreen.sistcoop.persona.client.models;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.persona.client.enums.Sexo;
import org.softgreen.sistcoop.persona.client.providers.Provider;

@Local
public interface PersonaNaturalProvider extends Provider {

	PersonaNaturalModel addPersonaNatural(
			String codigoPais,
			TipoDocumentoModel tipoDocumentoModel,
			String numeroDocumento,	
			String apellidoPaterno,
			String apellidoMaterno,
			String nombres,
			Date fechaNacimiento,
			Sexo sexo);

	boolean removePersonaNatural(PersonaNaturalModel personaNaturalModel);

	PersonaNaturalModel getPersonaNaturalById(Long id);

	PersonaNaturalModel getPersonaNaturalByTipoNumeroDoc(TipoDocumentoModel tipoDocumento, String numeroDocumento);

	List<PersonaNaturalModel> getPersonasNaturales();

	int getPersonasNaturalesCount();

	List<PersonaNaturalModel> getPersonasNaturales(int firstResult, int maxResults);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento);

	List<PersonaNaturalModel> searchForNumeroDocumento(String numeroDocumento, int firstResult, int maxResults);

	List<PersonaNaturalModel> searchForFilterText(String filterText);

	List<PersonaNaturalModel> searchForFilterText(String filterText, int firstResult, int maxResults);

}
