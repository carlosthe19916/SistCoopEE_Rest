package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.util.Date;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.models.DetalleTransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaModel;

public class TransaccionInternaAdapter implements TransaccionInternaModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Date getFecha() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Date getHora() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEstado() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getObservacion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getEstadoSolicitud() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean getEstadoConfirmacion() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setEstadoConfirmacion() {
		// TODO Auto-generated method stub

	}

	@Override
	public List<DetalleTransaccionInternaModel> getDetalle() {
		// TODO Auto-generated method stub
		return null;
	}

}
