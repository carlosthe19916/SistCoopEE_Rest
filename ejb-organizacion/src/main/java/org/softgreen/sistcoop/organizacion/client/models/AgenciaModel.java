package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

public interface AgenciaModel extends Model {

	Integer getId();

	String getCodigo();

	String getDenominacion();

	void setDenominacion(String denominacion);

	String getAbreviatura();

	void setAbreviatura(String abreviatura);

	String getUbigeo();

	void setUbigeo(String ubigeo);

	boolean getEstado();

	void desactivar();

	SucursalModel getSucursal();

	List<BovedaModel> getBovedas();

	List<BovedaModel> getBovedas(boolean estado);

	List<BovedaModel> getBovedas(String filterText, int firstResult, int maxResults);

	List<CajaModel> getCajas();

	List<CajaModel> getCajas(boolean estado);

	List<CajaModel> getCajas(String filterText, int firstResult, int maxResults);

	List<TrabajadorModel> getTrabajadores();

	List<TrabajadorModel> getTrabajadores(boolean estado);

	List<TrabajadorModel> getTrabajadores(String filterText, int firstResult, int maxResults);

}