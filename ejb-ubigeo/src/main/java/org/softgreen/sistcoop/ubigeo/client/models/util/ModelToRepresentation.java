package org.softgreen.sistcoop.ubigeo.client.models.util;

import org.softgreen.sistcoop.ubigeo.client.models.CountryModel;
import org.softgreen.sistcoop.ubigeo.client.models.CurrencyModel;
import org.softgreen.sistcoop.ubigeo.client.models.DenominationModel;
import org.softgreen.sistcoop.ubigeo.client.models.DepartamentoModel;
import org.softgreen.sistcoop.ubigeo.client.models.DistritoModel;
import org.softgreen.sistcoop.ubigeo.client.models.ExchangeRateModel;
import org.softgreen.sistcoop.ubigeo.client.models.ProvinciaModel;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.CountryRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.CurrencyRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DenominationRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DepartamentoRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.DistritoRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.ExchangeRateRepresentation;
import org.softgreen.sistcoop.ubigeo.client.representations.idm.ProvinciaRepresentation;

public class ModelToRepresentation {

	public static CountryRepresentation toRepresentation(CountryModel model) {
		if (model == null)
			return null;
		CountryRepresentation rep = new CountryRepresentation();

		rep.setAlpha2Code(model.getAlpha2Code());
		rep.setAlpha3Code(model.getAlpha3Code());
		rep.setFullName(model.getFullName());
		rep.setIndependent(model.isIndependent());
		rep.setNumericCode(model.getNumericCode());
		rep.setRemarks(model.getRemarks());
		rep.setShortName(model.getShortName());
		rep.setShortNameLowerCase(model.getShortNameLowerCase());
		rep.setStatus(model.getStatus());
		rep.setTerritoryName(model.getTerritoryName());

		return rep;
	}

	public static DepartamentoRepresentation toRepresentation(DepartamentoModel model) {
		if (model == null)
			return null;
		DepartamentoRepresentation rep = new DepartamentoRepresentation();

		rep.setId(model.getId());
		rep.setCodigo(model.getCodigo());
		rep.setDenominacion(model.getDenominacion());

		return rep;
	}

	public static ProvinciaRepresentation toRepresentation(ProvinciaModel model) {
		if (model == null)
			return null;
		ProvinciaRepresentation rep = new ProvinciaRepresentation();

		rep.setId(model.getId());
		rep.setCodigo(model.getCodigo());
		rep.setDenominacion(model.getDenominacion());
		rep.setDepartamento(model.getDepartamento().getCodigo());

		return rep;
	}

	public static DistritoRepresentation toRepresentation(DistritoModel model) {
		if (model == null)
			return null;
		DistritoRepresentation rep = new DistritoRepresentation();

		rep.setId(model.getId());
		rep.setCodigo(model.getCodigo());
		rep.setDenominacion(model.getDenominacion());

		rep.setProvincia(model.getProvincia().getCodigo());
		return rep;
	}

	public static CurrencyRepresentation toRepresentation(CurrencyModel model) {
		if (model == null)
			return null;
		CurrencyRepresentation rep = new CurrencyRepresentation();

		rep.setCode(model.getCode());
		rep.setDenomination(model.getDenomination());
		rep.setSimbol(model.getSimbol());

		return rep;
	}
	
	public static DenominationRepresentation toRepresentation(DenominationModel model) {
		if (model == null)
			return null;
		DenominationRepresentation rep = new DenominationRepresentation();
		rep.setValue(model.getValue());
		return rep;
	}
	
	public static ExchangeRateRepresentation toRepresentation(ExchangeRateModel model) {
		if (model == null)
			return null;
		ExchangeRateRepresentation rep = new ExchangeRateRepresentation();
		
		CurrencyModel currencyOriginModel = model.getCurrencyOrigin();
		CurrencyModel currencyDestinyModel = model.getCurrencyDestiny();
		
		rep.setCurrencyOrigin(toRepresentation(currencyOriginModel));
		rep.setCurrencyDestiny(toRepresentation(currencyDestinyModel));
		rep.setFecha(model.getFecha());
		rep.setValor(model.getValor());
		
		return rep;
	}

}
