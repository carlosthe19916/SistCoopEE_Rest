package org.softgreen.sistcoop.organizacion.ejb.models.jpa;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.models.DetalleTransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaModel;

public class DetalleTransaccionInternaAdapter implements DetalleTransaccionInternaModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void commit() {
		// TODO Auto-generated method stub

	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BigDecimal getValor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setValor(BigDecimal valor) {
		// TODO Auto-generated method stub

	}

	@Override
	public int getCantidad() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setCantidad(int valor) {
		// TODO Auto-generated method stub

	}

	@Override
	public TransaccionInternaModel getTransaccion() {
		// TODO Auto-generated method stub
		return null;
	}

}
