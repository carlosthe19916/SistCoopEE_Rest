package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;
import java.util.List;

public interface TransaccionInternaModel extends Model {

	Date getFecha();

	Date getHora();

	String getObservacion();

	boolean getEstado();

	void desactivar();

	boolean getEstadoSolicitud();

	boolean getEstadoConfirmacion();

	void confirmar();

	List<DetalleTransaccionInternaModel> getDetalle();

}
