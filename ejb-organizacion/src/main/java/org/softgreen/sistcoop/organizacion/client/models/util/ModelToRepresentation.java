package org.softgreen.sistcoop.organizacion.client.models.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;

public class ModelToRepresentation {

	/**
	 * Devuelve un objeto SucursalRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static SucursalRepresentation toRepresentation(SucursalModel model) {
		if (model == null)
			return null;
		SucursalRepresentation rep = new SucursalRepresentation();

		rep.setId(model.getId());
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setEstado(model.getEstado());

		return rep;
	}

	/**
	 * Devuelve un objeto AgenciaRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static AgenciaRepresentation toRepresentation(AgenciaModel model) {
		if (model == null)
			return null;
		AgenciaRepresentation rep = new AgenciaRepresentation();
		rep.setId(model.getId());
		rep.setAbreviatura(model.getAbreviatura());
		rep.setDenominacion(model.getDenominacion());
		rep.setCodigo(model.getCodigo());
		rep.setEstado(model.getEstado());
		rep.setUbigeo(model.getUbigeo());

		SucursalModel sucursalModel = model.getSucursal();
		SucursalRepresentation sucursalRepresentation = toRepresentation(sucursalModel);
		rep.setSucursal(sucursalRepresentation);
		return rep;
	}

	/**
	 * Devuelve un objeto BovedaRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static BovedaRepresentation toRepresentation(BovedaModel model) {
		if (model == null)
			return null;
		BovedaRepresentation rep = new BovedaRepresentation();
		rep.setId(model.getId());
		rep.setMoneda(model.getMoneda());
		rep.setDenominacion(model.getDenominacion());
		rep.setAbierto(model.isAbierto());
		rep.setEstadoMovimiento(model.getEstadoMovimiento());
		rep.setEstado(model.getEstado());

		HistorialModel historialModel = model.getHistorialActivo();
		rep.setSaldo((historialModel != null ? historialModel.getSaldo() : BigDecimal.ZERO));

		AgenciaModel agenciaModel = model.getAgencia();
		AgenciaRepresentation agenciaRepresentation = new AgenciaRepresentation();
		agenciaRepresentation.setId(agenciaModel.getId());
		agenciaRepresentation.setDenominacion(agenciaModel.getDenominacion());
		agenciaRepresentation.setCodigo(agenciaModel.getCodigo());
		rep.setAgencia(agenciaRepresentation);

		return rep;
	}

	/**
	 * Devuelve un objeto CajaRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static CajaRepresentation toRepresentation(CajaModel model) {
		if (model == null)
			return null;
		CajaRepresentation rep = new CajaRepresentation();
		rep.setId(model.getId());
		rep.setDenominacion(model.getDenominacion());
		rep.setAbierto(model.isAbierto());
		rep.setEstadoMovimiento(model.getEstadoMovimiento());
		rep.setEstado(model.getEstado());

		AgenciaModel agenciaModel = model.getAgencia();
		AgenciaRepresentation agenciaRepresentation = ModelToRepresentation.toRepresentation(agenciaModel);
		rep.setAgencia(agenciaRepresentation);

		List<BovedaRepresentation> bovedasAsignadas = new ArrayList<BovedaRepresentation>();
		List<BovedaCajaModel> bovedasCajas = model.getBovedaCajas();
		for (BovedaCajaModel bovedaCajaModel : bovedasCajas) {
			BovedaModel bovedaModel = bovedaCajaModel.getBoveda();
			HistorialModel historialActivo = bovedaModel.getHistorialActivo();
			BovedaRepresentation bovedaRepresentation = new BovedaRepresentation();
			bovedaRepresentation.setId(bovedaModel.getId());
			bovedaRepresentation.setDenominacion(bovedaModel.getDenominacion());
			bovedaRepresentation.setMoneda(bovedaModel.getMoneda());
			bovedaRepresentation.setAbierto(bovedaModel.isAbierto());
			bovedaRepresentation.setEstadoMovimiento(bovedaModel.getEstadoMovimiento());
			bovedaRepresentation.setEstado(bovedaModel.getEstado());
			bovedaRepresentation.setSaldo(historialActivo != null ? historialActivo.getSaldo() : BigDecimal.ZERO);
			bovedasAsignadas.add(bovedaRepresentation);
		}
		rep.setBovedas(bovedasAsignadas);

		List<TrabajadorRepresentation> trabajadoresAsignados = new ArrayList<TrabajadorRepresentation>();
		List<TrabajadorCajaModel> trabajadorCajas = model.getTrabajadorCajas();
		for (TrabajadorCajaModel trabajadorCajaModel : trabajadorCajas) {
			TrabajadorModel trabajadorModel = trabajadorCajaModel.getTrabajador();
			trabajadoresAsignados.add(toRepresentation(trabajadorModel));
		}
		rep.setTrabajadores(trabajadoresAsignados);
		return rep;
	}

	public static BovedaCajaRepresentation toRepresentation(BovedaCajaModel model) {
		if (model == null)
			return null;
		BovedaCajaRepresentation rep = new BovedaCajaRepresentation();
		rep.setId(model.getId());		
		rep.setEstado(model.getEstado());

		HistorialModel historialModel = model.getHistorialActivo();
		rep.setSaldo((historialModel != null ? historialModel.getSaldo() : BigDecimal.ZERO));		

		return rep;
	}
	
	/**
	 * Devuelve un objeto TrabajadorRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static TrabajadorRepresentation toRepresentation(TrabajadorModel model) {
		if (model == null)
			return null;
		TrabajadorRepresentation rep = new TrabajadorRepresentation();
		rep.setId(model.getId());
		rep.setTipoDocumento(model.getTipoDocumento());
		rep.setNumeroDocumento(model.getNumeroDocumento());
		rep.setUsuario(model.getUsuario());
		rep.setEstado(model.getEstado());

		AgenciaModel agenciaModel = model.getAgencia();
		AgenciaRepresentation agenciaRepresentation = toRepresentation(agenciaModel);
		rep.setAgencia(agenciaRepresentation);

		List<TrabajadorCajaModel> trabajadorCajaModels = model.getTrabajadorCajas();
		if (trabajadorCajaModels.size() > 0) {
			CajaModel cajaModel = trabajadorCajaModels.get(0).getCaja();
			CajaRepresentation cajaRepresentation = new CajaRepresentation();
			cajaRepresentation.setId(cajaModel.getId());
			cajaRepresentation.setDenominacion(cajaModel.getDenominacion());
			cajaRepresentation.setAbierto(cajaModel.isAbierto());
			cajaRepresentation.setEstadoMovimiento(cajaModel.getEstadoMovimiento());
			cajaRepresentation.setEstado(cajaModel.getEstado());
			rep.setCaja(cajaRepresentation);
		}

		return rep;
	}

	/**
	 * Devuelve un objeto DetalleHistorialRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static DetalleHistorialRepresentation toRepresentation(DetalleHistorialModel model) {
		if (model == null)
			return null;
		DetalleHistorialRepresentation rep = new DetalleHistorialRepresentation();

		rep.setId(model.getId());
		rep.setValor(model.getValor());
		rep.setCantidad(model.getCantidad());
		return rep;
	}

}
