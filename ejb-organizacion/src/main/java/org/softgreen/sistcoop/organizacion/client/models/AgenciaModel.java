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

	void setEstado(boolean estado);

	/**
	 * @return sucursal a la que pertenece la Agencia.
	 */
	SucursalModel getSucursal();

	/**
	 * @return bovedas activas de la agencia.
	 */
	List<BovedaModel> getBovedas();

	/**
	 * @return bovedas activas/inactivas segun el parametro enviado.
	 * 
	 * @param estado
	 */
	List<BovedaModel> getBovedas(boolean estado);

	/**
	 * @return busca bovedas activas segun el filtro y limites de resultado
	 *         enviados.
	 * 
	 * @param filterText
	 * @param limit
	 * @param offset
	 */
	List<BovedaModel> getBovedas(String filterText, int firstResult, int maxResults);

	/**
	 * @return cajas activas de la agencia.
	 */
	List<CajaModel> getCajas();

	/**
	 * @return cajas activas/inactivas segun el parametro enviado.
	 * 
	 * @param estado
	 */
	List<CajaModel> getCajas(boolean estado);

	/**
	 * @return busca cajas activas segun el filtro y limites de resultado
	 *         enviados.
	 * 
	 * @param filterText
	 * @param limit
	 * @param offset
	 */
	List<CajaModel> getCajas(String filterText, int firstResult, int maxResults);

	/**
	 * @return trabajadores activos de la agencia.
	 */
	List<TrabajadorModel> getTrabajadores();

	/**
	 * @return trabajadores activos/inactivos segun el parametro enviado.
	 * 
	 * @param estado
	 */
	List<TrabajadorModel> getTrabajadores(boolean estado);

	/**
	 * @return busca trabajadores activos segun el filtro y limites de resultado
	 *         enviados.
	 * 
	 * @param filterText
	 * @param limit
	 * @param offset
	 */
	List<TrabajadorModel> getTrabajadores(String filterText, int firstResult, int maxResults);

}