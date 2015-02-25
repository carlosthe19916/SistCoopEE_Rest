package org.softgreen.sistcoop.organizacion.client.models.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.softgreen.sistcoop.organizacion.client.models.AgenciaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.BovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.CajaModel;
import org.softgreen.sistcoop.organizacion.client.models.DetalleHistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.EntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.HistorialModel;
import org.softgreen.sistcoop.organizacion.client.models.PendienteCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.SucursalModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TrabajadorModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaBovedaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionBovedaEntidadModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionCajaCajaModel;
import org.softgreen.sistcoop.organizacion.client.models.TransaccionInternaModel;
import org.softgreen.sistcoop.organizacion.client.representations.idm.AgenciaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.BovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.CajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.DetalleHistorialRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.EntidadRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.PendienteCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.SucursalRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TrabajadorRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionBovedaBovedaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionBovedaCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionBovedaEntidadRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionCajaCajaRepresentation;
import org.softgreen.sistcoop.organizacion.client.representations.idm.TransaccionInternaRepresentation;

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

	/**
	 * Devuelve un objeto DetalleHistorialRepresentation a partir de un model.
	 * 
	 * @param model
	 */
	public static PendienteCajaRepresentation toRepresentation(PendienteCajaModel model) {
		if (model == null)
			return null;
		PendienteCajaRepresentation rep = new PendienteCajaRepresentation();

		rep.setId(model.getId());
		rep.setFecha(model.getFecha());
		rep.setHora(model.getHora());
		rep.setTrabajador(model.getTrabajador());
		rep.setObservacion(model.getObservacion());

		return rep;
	}

	public static TransaccionInternaRepresentation toRepresentation(TransaccionInternaModel model) {
		if (model == null)
			return null;
		
		if(model instanceof TransaccionBovedaEntidadRepresentation){
			TransaccionBovedaEntidadModel modelBovedaEntidad = (TransaccionBovedaEntidadModel) model;
			TransaccionBovedaEntidadRepresentation rep = new TransaccionBovedaEntidadRepresentation();
			
			rep.setId(modelBovedaEntidad.getId());
			rep.setFecha(model.getFecha());
			rep.setHora(model.getHora());
			rep.setEstadoSolicitud(model.getEstadoSolicitud());
			rep.setEstadoConfirmacion(model.getEstadoConfirmacion());
			rep.setObservacion(model.getObservacion());
			rep.setEntidad(toRepresentation(modelBovedaEntidad.getEntidad()));
			rep.setBovedaOrigen(toRepresentation(modelBovedaEntidad.getBoveda()));
			rep.setEstado(model.getEstado());		
			
			return rep;
		} else if (model instanceof TransaccionBovedaBovedaModel){
			TransaccionBovedaBovedaModel modelBovedaBoveda = (TransaccionBovedaBovedaModel) model;
			TransaccionBovedaBovedaRepresentation rep = new TransaccionBovedaBovedaRepresentation();
			
			rep.setId(modelBovedaBoveda.getId());
			rep.setFecha(model.getFecha());
			rep.setHora(model.getHora());
			rep.setEstadoSolicitud(model.getEstadoSolicitud());
			rep.setEstadoConfirmacion(model.getEstadoConfirmacion());
			rep.setObservacion(model.getObservacion());
			rep.setBovedaOrigen(toRepresentation(modelBovedaBoveda.getOrigen()));
			rep.setBovedaDestino(toRepresentation(modelBovedaBoveda.getDestino()));
			rep.setEstado(model.getEstado());		
			
			return rep;
		} else if (model instanceof TransaccionBovedaCajaModel){
			TransaccionBovedaCajaModel modelBovedaCaja = (TransaccionBovedaCajaModel) model;
			TransaccionBovedaCajaRepresentation rep = new TransaccionBovedaCajaRepresentation();
			
			rep.setId(modelBovedaCaja.getId());
			rep.setFecha(model.getFecha());
			rep.setHora(model.getHora());
			rep.setEstadoSolicitud(model.getEstadoSolicitud());
			rep.setEstadoConfirmacion(model.getEstadoConfirmacion());
			rep.setObservacion(model.getObservacion());
			rep.setBoveda(toRepresentation(modelBovedaCaja.getBoveda()));
			rep.setCaja(toRepresentation(modelBovedaCaja.getCaja()));
			rep.setEstado(model.getEstado());		
			
			return rep;	
		}			
		else if (model instanceof TransaccionCajaCajaModel){
			TransaccionCajaCajaModel modelCajaCaja = (TransaccionCajaCajaModel) model;
			TransaccionCajaCajaRepresentation rep = new TransaccionCajaCajaRepresentation();
			
			rep.setId(modelCajaCaja.getId());
			rep.setFecha(model.getFecha());
			rep.setHora(model.getHora());
			rep.setEstadoSolicitud(model.getEstadoSolicitud());
			rep.setEstadoConfirmacion(model.getEstadoConfirmacion());
			rep.setObservacion(model.getObservacion());		
			rep.setCajaOrigen(toRepresentation(modelCajaCaja.getCajaOrigen()));
			rep.setCajaDestino(toRepresentation(modelCajaCaja.getCajaDestino()));
			rep.setEstado(model.getEstado());		
			
			return rep;	
		}		
		
		return null;
	}

	private static EntidadRepresentation toRepresentation(EntidadModel entidad) {
		// TODO Auto-generated method stub
		return null;
	}
}
