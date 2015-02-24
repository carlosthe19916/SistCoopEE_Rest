package org.softgreen.sistcoop.organizacion.client.models;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import org.softgreen.sistcoop.organizacion.client.providers.Provider;

@Local
public interface TransaccionInternaProvider extends Provider {

	TransaccionInternaModel addTransaccionInterna();

	TransaccionInternaModel getTransaccionInternaById(Long id);

}
