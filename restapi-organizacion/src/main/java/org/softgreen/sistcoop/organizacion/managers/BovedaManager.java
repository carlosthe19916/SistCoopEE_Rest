package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.List;
import java.util.Set;

import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaProvider;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialProvider;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialProvider;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyProvider;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class BovedaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected HistorialProvider historialProvider;

	@Inject
	protected DetalleHistorialProvider detalleHistorialProvider;

	@Inject
	protected CurrencyProvider currencyProvider;

	public void desactivarBoveda(BovedaModel model) {
		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			bovCajModel.desactivar();
			bovCajModel.commit();
		}

		model.desactivar();
		model.setEstadoMovimiento(false);
		model.commit();
	}

	public void abrir(BovedaModel bovedaModel) {
		List<BovedaCajaModel> bovedaCajaModels = bovedaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			if (cajaModel.isAbierto())
				throw new EJBException("Caja asociada abierta, no se puede abrir");
		}

		HistorialModel historialActivoModel = bovedaModel.getHistorialActivo();
		Calendar calendar = Calendar.getInstance();
		if (historialActivoModel == null) {
			HistorialModel historialNewModel = historialProvider.addHistorial(bovedaModel);
			String moneda = bovedaModel.getMoneda();

			CurrencyModel currencyModel = currencyProvider.findByCode(moneda);
			Set<DenominationModel> denominationModels = currencyModel.getDenominations();
			for (DenominationModel denominationModel : denominationModels) {
				int cantidad = 0;
				BigDecimal valor = denominationModel.getValue();
				detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
			}
		} else {
			List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();

			historialActivoModel.desactivar();
			historialActivoModel.setFechaCierre(calendar.getTime());
			historialActivoModel.setHoraCierre(calendar.getTime());

			HistorialModel historialNewModel = historialProvider.addHistorial(bovedaModel);
			for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
				int cantidad = detalleHistorialActivoModel.getCantidad();
				BigDecimal valor = detalleHistorialActivoModel.getValor();
				detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
			}

			// añadir denominaciones que no existen actualmente en el historial
			// pero que si existen en la moneda
			String moneda = bovedaModel.getMoneda();
			CurrencyModel currencyModel = currencyProvider.findByCode(moneda);
			Set<DenominationModel> denominationModels = currencyModel.getDenominations();
			for (DenominationModel denominationModel : denominationModels) {
				BigDecimal valorPorRegistrar = denominationModel.getValue();
				boolean exists = false;
				for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
					BigDecimal valorRegistrado = detalleHistorialActivoModel.getValor();
					if (valorRegistrado.equals(valorPorRegistrar)) {
						exists = true;
					}
				}
				if (!exists) {
					detalleHistorialProvider.addDetalleHistorial(historialNewModel, 0, valorPorRegistrar);
				}
			}

			historialActivoModel.commit();
		}

		bovedaModel.setAbierto(true);
		bovedaModel.setEstadoMovimiento(false);
		bovedaModel.commit();
	}

	public void cerrar(BovedaModel bovedaModel) {
		if (!bovedaModel.isAbierto()) {
			throw new EJBException("Boveda cerrada, no se puede cerrar nuevamente.");
		}
		if (!bovedaModel.getEstado()) {
			throw new EJBException("Boveda inactiva, no se puede cerrar.");
		}

		List<BovedaCajaModel> bovedaCajaModels = bovedaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			CajaModel cajaModel = bovedaCajaModel.getCaja();
			if (cajaModel.isAbierto())
				throw new EJBException("Caja asociada abierta, no se puede cerrar.");
		}

		HistorialModel historialActivoModel = bovedaModel.getHistorialActivo();
		Calendar calendar = Calendar.getInstance();
		if (historialActivoModel != null) {
			bovedaModel.setAbierto(false);
			bovedaModel.setEstadoMovimiento(false);
			bovedaModel.commit();

			historialActivoModel.setFechaCierre(calendar.getTime());
			historialActivoModel.setHoraCierre(calendar.getTime());
			historialActivoModel.commit();
		} else {
			throw new EJBException("No se encontró un historial activo, informe al area de sistemas.");
		}
	}

	public void congelar(BovedaModel bovedaModel) {
		if (!bovedaModel.isAbierto()) {
			throw new EJBException("Boveda cerrada, no se puede congelar.");
		}
		if (!bovedaModel.getEstado()) {
			throw new EJBException("Boveda inactiva, no se puede congelar.");
		}

		bovedaModel.setEstadoMovimiento(false);
		bovedaModel.commit();
	}

	public void descongelar(BovedaModel bovedaModel) {
		if (!bovedaModel.isAbierto()) {
			throw new EJBException("Boveda cerrada, no se puede congelar.");
		}
		if (!bovedaModel.getEstado()) {
			throw new EJBException("Boveda inactiva, no se puede congelar.");
		}

		bovedaModel.setEstadoMovimiento(true);
		bovedaModel.commit();
	}
}
