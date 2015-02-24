package org.softgreen.sistcoop.organizacion.client.models;

import java.util.Date;
import java.util.List;

public interface TransaccionInternaModel extends Model {

	Date getFecha();

	Date getHora();

	String getObservacion();

	boolean getEstado();

	void setEstado(boolean estado);

	boolean getEstadoSolicitud();

	boolean setEstadoSolicitud(boolean estado);

	boolean getEstadoConfirmacion();

	void setEstadoConfirmacion(boolean estado);

	List<DetalleTransaccionInternaModel> getDetalle();

}
