package org.softgreen.sistcoop.persona.client.models;

import java.util.Date;

import org.softgreen.sistcoop.persona.client.enums.EstadoCivil;
import org.softgreen.sistcoop.persona.client.enums.Sexo;

public interface PersonaNaturalModel extends Model {

	Long getId();

	String getCodigoPais();

	void setCodigoPais(String codigoPais);

	TipoDocumentoModel getTipoDocumento();

	void setTipoDocumento(TipoDocumentoModel tipoDocumento);

	String getNumeroDocumento();

	void setNumeroDocumento(String numeroDocumento);

	String getApellidoPaterno();

	void setApellidoPaterno(String apellidoPaterno);

	String getApellidoMaterno();

	void setApellidoMaterno(String apellidoMaterno);

	String getNombres();

	void setNombres(String nombres);

	Date getFechaNacimiento();

	void setFechaNacimiento(Date fechaNacimiento);

	Sexo getSexo();

	void setSexo(Sexo sexo);

	EstadoCivil getEstadoCivil();

	void setEstadoCivil(EstadoCivil estadoCivil);

	String getOcupacion();

	void setOcupacion(String ocupacion);

	String getUrlFoto();

	void setUrlFoto(String urlFoto);

	String getUrlFirma();

	void setUrlFirma(String urlFirma);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	String getDireccion();

	void setDireccion(String direccion);

	String getReferencia();

	void setReferencia(String referencia);

	String getTelefono();

	void setTelefono(String telefono);

	String getCelular();

	void setCelular(String celular);

	String getEmail();

	void setEmail(String email);

}