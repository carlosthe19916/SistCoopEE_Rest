package org.softgreen.sistcoop.organizacion.client.models;

import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface SucursalProvider extends Provider {

	SucursalModel addSucursal(String abreviatura, String denominacion);

	boolean removeSucursal(SucursalModel sucursalModel);

	SucursalModel getSucursalById(Integer id);
	
	SucursalModel getSucursalByDenominacion(String denominacion);

	List<SucursalModel> getSucursales();

	List<SucursalModel> getSucursales(boolean estado);	
	
	List<SucursalModel> getSucursales(String filterText, int limit, int offset);
}
