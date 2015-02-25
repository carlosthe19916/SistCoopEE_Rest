package org.softgreen.sistcoop.organizacion.client.models;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface TransaccionInternaProvider extends Provider {

	TransaccionInternaModel addTransaccionInterna(EntidadModel entidadModel, BovedaModel bovedaModel, String observacion);

	TransaccionInternaModel addTransaccionInterna(BovedaModel bovedaModelOrigen, BovedaModel bovedaModelDestino, String observacion);

	TransaccionInternaModel addTransaccionInterna(BovedaModel bovedaModelOrigen, CajaModel cajaModel, String observacion);

	TransaccionInternaModel addTransaccionInterna(CajaModel cajaModelOrigen, CajaModel cajaModelDestino, String observacion);

	TransaccionInternaModel getTransaccionInternaById(Long id);

}
