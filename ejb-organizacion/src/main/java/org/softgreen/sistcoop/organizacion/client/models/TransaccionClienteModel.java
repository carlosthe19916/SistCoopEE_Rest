package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;
import java.util.List;


public interface TransaccionClienteModel extends Model {

	Long getId();

	Long getNumeroOperacion();

	Date getFecha();

	Date getHora();

	boolean getEstado();

	void desactivar();

	String getObservacion();

	void setObservacion();

	HistorialBovedaCajaModel getHistorial();

	List<DetalleTransaccionClienteModel> getDetalle();

}
