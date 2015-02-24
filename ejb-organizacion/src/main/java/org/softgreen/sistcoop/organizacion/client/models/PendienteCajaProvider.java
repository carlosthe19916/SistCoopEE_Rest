package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface PendienteCajaProvider extends Provider {

	PendienteCajaModel addPendiente(HistorialBovedaCajaModel historialBovedaCajaModel, Date fecha, Date hora, BigDecimal monto, String trabajador);

	PendienteCajaModel getPendienteCajaById(Long id);

	List<PendienteCajaModel> getPendientesCaja(HistorialBovedaCajaModel historialBovedaCajaModel);

}
