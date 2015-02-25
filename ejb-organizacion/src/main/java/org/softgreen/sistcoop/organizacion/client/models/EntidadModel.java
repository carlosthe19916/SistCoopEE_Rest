package org.softgreen.sistcoop.organizacion.client.models;

public interface EntidadModel extends Model {

	public Integer getId();

	public String getDenominacion();

	public void setDenominacion(String denominacion);

	public String getAbreviatura();

	public void setAbreviatura(String abreviatura);

	public boolean getEstado();

	public void desactivar();

}
