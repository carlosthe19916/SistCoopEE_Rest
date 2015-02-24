package org.softgreen.sistcoop.organizacion.restapi.resources;

import javax.ejb.Stateless;
import javax.ws.rs.Path;

import org.jboss.ejb3.annotation.SecurityDomain;

@Path("/transaccionInterna")
@Stateless
@SecurityDomain("keycloak")
public class TransaccionBovedaCajaResource {

}