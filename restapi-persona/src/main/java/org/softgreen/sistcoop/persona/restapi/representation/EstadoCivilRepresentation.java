package org.softgreen.sistcoop.persona.restapi.representation;

public class EstadoCivilRepresentation {

	String denominacion;

	public EstadoCivilRepresentation(String denominacion) {
		this.denominacion = denominacion;
	}

	public String getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(String denominacion) {
		this.denominacion = denominacion;
	}
}
