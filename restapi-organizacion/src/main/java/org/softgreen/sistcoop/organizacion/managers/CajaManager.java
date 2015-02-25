package org.softgreen.sistcoop.organizacion.managers;

import java.math.BigDecimal;
import java.util.ArrayList;
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
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaProvider;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.restapi.representation.DetalleHistorialCajaRepresentation;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyProvider;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;

@Stateless
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class CajaManager {

	@Inject
	protected BovedaCajaProvider bovedaCajaProvider;

	@Inject
	protected TrabajadorCajaProvider trabajadorCajaProvider;

	@Inject
	protected HistorialProvider historialProvider;

	@Inject
	protected DetalleHistorialProvider detalleHistorialProvider;

	@Inject
	protected CurrencyProvider currencyProvider;

	public BovedaCajaModel addBoveda(CajaModel cajaModel, BovedaModel bovedaModel) {
		BovedaCajaModel bovedaCajaModel = bovedaCajaProvider.addBovedaCaja(bovedaModel, cajaModel);
		return bovedaCajaModel;
	}

	public void desactivarBovedaCaja(BovedaCajaModel bovedaCajaModel) {
		BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
		CajaModel cajaModel = bovedaCajaModel.getCaja();
		if (bovedaModel.isAbierto())
			throw new EJBException("Boveda abierta, no se puede desactivar");
		if (cajaModel.isAbierto())
			throw new EJBException("Caja abierta, no se puede desactivar");

		BigDecimal saldo = bovedaCajaModel.getSaldo();
		if (saldo.compareTo(BigDecimal.ZERO) != 0) {
			throw new EJBException("La caja tiene saldo asignado diferente de cero.");
		}

		bovedaCajaModel.desactivar();
		bovedaCajaModel.commit();
	}

	public void desactivarCaja(CajaModel model) {
		if (model.isAbierto())
			throw new EJBException("Caja abierta, no se puede desactivar");

		List<BovedaCajaModel> list = model.getBovedaCajas();
		for (BovedaCajaModel bovCajModel : list) {
			desactivarBovedaCaja(bovCajModel);

			BigDecimal saldo = bovCajModel.getSaldo();
			if (saldo.compareTo(BigDecimal.ZERO) != 0) {
				throw new EJBException("La caja tiene saldo asignado diferente de cero.");
			}
		}

		List<TrabajadorCajaModel> list2 = model.getTrabajadorCajas();
		for (TrabajadorCajaModel trabCajModel : list2) {
			trabajadorCajaProvider.removeTrabajadorCaja(trabCajModel);
		}

		model.desactivar();
		model.setEstadoMovimiento(false);
		model.commit();

	}

	public void abrir(CajaModel cajaModel) {
		if (cajaModel.isAbierto()) {
			throw new EJBException("Caja abierta, no se puede abrir nuevamente.");
		}
		if (cajaModel.getEstadoMovimiento()) {
			throw new EJBException("Caja descongelada, no se puede abrir.");
		}
		if (!cajaModel.getEstado()) {
			throw new EJBException("Caja inactiva, no se puede abrir.");
		}

		List<BovedaCajaModel> bovedaCajaModels = cajaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			if (!bovedaModel.isAbierto())
				throw new EJBException("Boveda asociada cerrada, no se puede abrir");
		}

		List<HistorialModel> historialesActivos = new ArrayList<HistorialModel>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
			if (historialModel != null)
				historialesActivos.add(historialModel);
		}

		boolean firstTime;
		if (historialesActivos.size() == 0) {
			firstTime = true;
		} else if (historialesActivos.size() == bovedaCajaModels.size()) {
			firstTime = false;
		} else {
			throw new EJBException("Error interno, existen cajas que no tienen historiales. Pongase en contacto con el area de sistemas.");
		}

		Calendar calendar = Calendar.getInstance();
		if (firstTime) {
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				HistorialModel historialModel = historialProvider.addHistorial(bovedaCajaModel);

				BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
				String moneda = bovedaModel.getMoneda();

				CurrencyModel currencyModel = currencyProvider.findByCode(moneda);
				Set<DenominationModel> denominationModels = currencyModel.getDenominations();
				for (DenominationModel denominationModel : denominationModels) {
					int cantidad = 0;
					BigDecimal valor = denominationModel.getValue();
					detalleHistorialProvider.addDetalleHistorial(historialModel, cantidad, valor);
				}
			}
		} else {
			for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
				HistorialModel historialActivoModel = bovedaCajaModel.getHistorialActivo();

				List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();

				historialActivoModel.desactivar();
				historialActivoModel.setFechaCierre(calendar.getTime());
				historialActivoModel.setHoraCierre(calendar.getTime());

				HistorialModel historialNewModel = historialProvider.addHistorial(bovedaCajaModel);
				for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
					int cantidad = detalleHistorialActivoModel.getCantidad();
					BigDecimal valor = detalleHistorialActivoModel.getValor();
					detalleHistorialProvider.addDetalleHistorial(historialNewModel, cantidad, valor);
				}

				historialActivoModel.commit();
			}
		}

		cajaModel.setAbierto(true);
		cajaModel.setEstadoMovimiento(false);
		cajaModel.commit();
	}

	public void cerrar(CajaModel cajaModel, List<DetalleHistorialCajaRepresentation> detalleHistorialCajaRepresentations) {
		if (!cajaModel.isAbierto()) {
			throw new EJBException("Caja cerrada, no se puede cerrar nuevamente.");
		}
		if (!cajaModel.getEstado()) {
			throw new EJBException("Caja inactiva, no se puede cerrar.");
		}

		List<BovedaCajaModel> bovedaCajaModels = cajaModel.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			if (!bovedaModel.isAbierto())
				throw new EJBException("Boveda asociada cerrada, no se puede cerrar");
		}

		List<HistorialModel> historialesActivos = new ArrayList<HistorialModel>();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			HistorialModel historialModel = bovedaCajaModel.getHistorialActivo();
			if (historialModel != null)
				historialesActivos.add(historialModel);
		}

		if (historialesActivos.size() != bovedaCajaModels.size()) {
			throw new EJBException("Error interno, boveda_caja no tiene historiales que cerrar. Pongase en contacto con el area de sistemas.");
		}

		// verificar que los saldos coincidan
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			HistorialModel historialActivoModel = bovedaCajaModel.getHistorialActivo();
			if (historialActivoModel == null) {
				throw new EJBException("Error interno, no se pudo encontrar un historial activo para la caja.");
			}

			DetalleHistorialCajaRepresentation detalleHistorialCajaRepresentation = getDetalleHistorialCajaRepresentationFromListDetalleHistorialCajaRepresentation(detalleHistorialCajaRepresentations, bovedaModel);
			if (detalleHistorialCajaRepresentation == null) {
				throw new EJBException("Error interno, no se pudo encontrar el detalle enviado.");
			}

			BigDecimal totalRepresentation = getTotalFromListDetalleHistorialRepresentation(detalleHistorialCajaRepresentation.getDetalleHistorial());			
			if (historialActivoModel.getSaldo().compareTo(totalRepresentation) != 0) {
				throw new EJBException("Error interno, el saldo enviado no coincide con el saldo del sistema.");
			}
		}

		// Escribir el nuevo historial
		Calendar calendar = Calendar.getInstance();
		for (BovedaCajaModel bovedaCajaModel : bovedaCajaModels) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			HistorialModel historialActivoModel = bovedaCajaModel.getHistorialActivo();

			List<DetalleHistorialModel> detalleHistorialActivoModels = historialActivoModel.getDetalle();
			
			DetalleHistorialCajaRepresentation detalleHistorialCajaRepresentation = getDetalleHistorialCajaRepresentationFromListDetalleHistorialCajaRepresentation(detalleHistorialCajaRepresentations, bovedaModel);
			List<DetalleHistorialRepresentation> detalleHistorialRepresentations = detalleHistorialCajaRepresentation.getDetalleHistorial();
			
			if(detalleHistorialActivoModels.size() != detalleHistorialRepresentations.size()){
				throw new EJBException("Error interno, el detalle enviado tiene una cantidad diferente de denominaciones que el sistema.");
			}
			
			// cambiando los detalles
			for (DetalleHistorialModel detalleHistorialActivoModel : detalleHistorialActivoModels) {
				BigDecimal valor = detalleHistorialActivoModel.getValor();				
				int cantidadNueva = getCantidadFromListDetalleHistorialRepresentation(detalleHistorialRepresentations, valor);
				if(cantidadNueva == -1)
					throw new EJBException("Error interno, no se pudo encontrar la denominacion especificada.");
				
				detalleHistorialActivoModel.setCantidad(cantidadNueva);
				detalleHistorialActivoModel.commit();
			}
			
			historialActivoModel.setFechaCierre(calendar.getTime());
			historialActivoModel.setHoraCierre(calendar.getTime());
			historialActivoModel.commit();
		}

		cajaModel.setAbierto(false);
		cajaModel.setEstadoMovimiento(false);
		cajaModel.commit();
	}

	public void congelar(CajaModel cajaModel) {

	}

	public void descongelar(CajaModel cajaModel) {

	}

	private DetalleHistorialCajaRepresentation getDetalleHistorialCajaRepresentationFromListDetalleHistorialCajaRepresentation(List<DetalleHistorialCajaRepresentation> detalle, BovedaModel bovedaModel) {
		DetalleHistorialCajaRepresentation result = null;
		for (DetalleHistorialCajaRepresentation detalleHistorialCajaRepresentation : detalle) {
			BovedaRepresentation bovedaRepresentation = detalleHistorialCajaRepresentation.getBoveda();
			if (bovedaModel.getId().equals(bovedaRepresentation.getId())) {
				result = detalleHistorialCajaRepresentation;
				break;
			}
		}
		return result;
	}

	private int getCantidadFromListDetalleHistorialRepresentation(List<DetalleHistorialRepresentation> detalleHistorialRepresentations, BigDecimal valor) {
		int cantidad = -1;
		for (DetalleHistorialRepresentation detalleHistorialRepresentation : detalleHistorialRepresentations) {
			BigDecimal valorDetalleHistorialRepresentation = detalleHistorialRepresentation.getValor();
			if (valor.compareTo(valorDetalleHistorialRepresentation) == 0) {
				cantidad = detalleHistorialRepresentation.getCantidad();
				break;
			}
		}
		return cantidad;
	}

	private BigDecimal getTotalFromListDetalleHistorialRepresentation(List<DetalleHistorialRepresentation> detalleHistorialRepresentations){
		BigDecimal result = BigDecimal.ZERO;
		for (DetalleHistorialRepresentation detalleHistorialRepresentation : detalleHistorialRepresentations) {
			int cantidad = detalleHistorialRepresentation.getCantidad();
			BigDecimal valor = detalleHistorialRepresentation.getValor();			
			BigDecimal subtotal = new BigDecimal(cantidad).multiply(valor);
			result = result.add(subtotal);
		}
		return result;
	}
	
}
